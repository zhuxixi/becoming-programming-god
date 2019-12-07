>这是Thread类的一个内部类，它是一个接口
## 相关内容
实现类:
* ThreadGroup

## 概述
当线程因为一个没有catch到的异常而终止时，可以通过这个接口的实现类处理一些后续工作。  
当线程因为异常终止时，JVM会查询这个线程的UncaughtExceptionHandler对象，并且调用handler's uncaughtException的方法，将thread对象和exception异常对象作为参数传递进去，如果Thread没有显示的setUncaughtExceptionHandler，那么这个线程的ThreadGroup会做为它的UncaughtExceptionHandler。如果你用的是默认的ThreadGroup，那么ThreadGroup的uncaughtException其实调用了thread.getDefaultUncaughtExceptionHandler，最终执行的uncaughtException角色是默认的ExceptionHandler。
## 方法

### void uncaughtException(Thread t,Throwable e)
如果线程由于异常而终止，此方法就会被JVM调用。此方法内部抛出的任何异常都会被系统忽略。这个方法是子线程执行任务出错后的救命稻草。

Parameters:  
t - the thread  
e - the exception

## 代码示例
### 场景
>为了演示这个Handler的用法，我们可以思考一下这个Handler的使用场景。

例如，每个业务系统一般都会包含一个**登陆服务**，登陆服务大多是一个独立的微服务。一般登录接口中都会做一些辅助操作，如**记录登录日志**等等，而且我们希望这类**辅助操作在出现异常的情况下，不会阻断登录主业务接口的正常返回**。

一个比较常规的做法是使用消息中间件将登录成功的用户信息封装成消息发送至远程服务异步处理。但是对于一个中小规模的业务系统，你引入了消息中间件，也就是MQ的话，会带来不小的维护成本，**俗称高射炮打蚊子**。还有一种比较通用的办法是将登录成功的日志消息放入一个Java阻塞队列，然后使用一个线程池分配**有限的资源**去慢慢地将日志信息写入数据库，这样登录请求正常返回，记录日志让线程池异步的去处理，这样你没有引入第三方的中间件，维护成本也不高，你只要学好Java就行了。  

这种方式虽然很方便，但是其实存在问题：
#### 写入数据库异常会导致丢消息
在进行数据库插入操作时，可能是数据库连接池资源不够，获取连接超时会抛出异常，此时执行这个任务的Thread就会终止，这条消息就等于丢掉了，导致你的登录日志记录不准确。为了避免这个问题，**我们可以使用Thread.UncaughtExceptionHandler在抛出异常后将日志记录再次放回阻塞队列**。

如果数据库连接池资源紧张的问题只是瞬时的，那这样ok，否则，还是会存在问题:

如果数据库连接池资源长时间紧张，线程入库持续抛异常、不停的将任务放回队列，而且登陆接口压力依然很高，导致队列消费速度跟不上队列增长速度，那么：
#### 如果使用有界队列做日志记录缓冲，队列会满，日志记录无法放入队列
#### 如果使用无界队列做日志记录缓冲，队列可能数据量过大导致内存被撑爆

那么我们该怎么办呢？  
这个其实还是要看具体的需求，如果详细的登录日志对你来说没有那么重要，那么你可以通过Thread.UncaughtExceptionHandler把登录日志放入另一个队列，然后定期对数据进行归并，因为登录次数这种数据每个系统几乎都要统计的。  
如果你不光要统计登录人数，具体谁登录了你也要知道，那你可以提前准备一个备用的数据库，在主数据库压力拉满的情况下将数据放入另一个队列写入备用数据库中。

>其实Thread.UncaughtExceptionHandler并不是处理这种情况的最佳办法，而且JDK不推荐我们显示的创建线程，多数情况下最好使用线程池。不过本文的重点是介绍Thread.UncaughtExceptionHandler，并且加深记忆。

>**另外，这种基于JDK内存队列的异步模式在服务挂掉的时候会导致消息大面积丢失，如果读者真的会遇到此类场景，而且你的用户量每日都有大量增长，建议直接使用MQ。只有用户量稳定，而且你能保证系统压力峰值绝对不会超过某个预估值时才推荐使用内存队列。**

### 相关代码
我们来写写代码实现上述上述场景

#### 首先，我们先创建一个实体类，它代表登录日志
```
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 登录日志对象，用于记录用户登录信息
 * @author zhuzh
 * @date 2019.10.17
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginLogPO {
    private int id;
    private String username;
    private String createTime;
}
```
#### 有了实体类，继续写DAO层的接口

```
/**
 * 登录日志持久层
 * @author zhuzh
 * @date 2019.10.17
 */
public interface LoginLoggingDAO {
    /**
     * 记录登录日志到数据库
     * @param po
     * @return
     */
    boolean log(LoginLogPO po);

    /**
     * 记录日志失败
     * @return
     */
    void logFailed();

    /**
     * 获取备用数据源中的日志数据量
     * @return
     */
    int getBackupDatasourceSize();
}
```

#### DAO层的模拟实现类，我们不会真的去插入数据库，而是将数据放入一个队列

```
/**
 * 一个登录日志持久层的模拟实现类
 * @author zhuzh
 * @date 2019.10.17
 */
public class MockLoginLoggingDAOImpl implements LoginLoggingDAO {
    /**
     * 使用定长队列来模拟数据库连接池资源紧张的场景
     * MAIN_DATASOURCE代表主数据源
     * 创建一个长度为5的队列，队列满了之后，向队列插入数据会阻塞，直到队列有空闲位置才可插入。
     */
    private static final ArrayBlockingQueue<LoginLogPO> MAIN_DATASOURCE = new ArrayBlockingQueue<>(5);

    /**
     * 类初始化时直接将MAIN_DATASOURCE塞满，这样线程再插入数据时就会抛出异常
     */
    static {
        System.out.println("第三步：将主数据源塞满，模拟主数据源连接池资源不足");
        for (int i = 0; i < 5; i++) {
            MAIN_DATASOURCE.offer(new LoginLogPO(i,"用户"+i,"2019-10-1"+i));
        }
    }


    /**
     * BACKUP_DATASOURCE代表备用数据源，主数据源不可用时程序会切换到备用数据源，长度为100
     */
    private static final ArrayBlockingQueue<LoginLogPO> BACKUP_DATASOURCE = new ArrayBlockingQueue<>(100);

    private static final AtomicInteger FAILED_COUNTER= new AtomicInteger();
    private static AtomicBoolean USE_BACKUP = new AtomicBoolean();


    @Override
    public boolean log(LoginLogPO po) {
        ArrayBlockingQueue<LoginLogPO> currentDatasource = getDatasource();
        try {
            boolean success = currentDatasource.offer(po,3, TimeUnit.SECONDS);
            if (!success){
                throw new DatasourceBusyException("主数据源繁忙，即将切换备用数据源!");
            }
            System.out.println("第五步：入库成功，入库成功的数据源为,currentDatasource="+(currentDatasource==BACKUP_DATASOURCE?"BACKUP_DATASOURCE":"MAIN_DATASOURCE"));
        }catch (InterruptedException e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public void logFailed() {
        //记录日志的失败次数+1
        int currentFailedCount = FAILED_COUNTER.incrementAndGet();
        //如果已经失败10次了,说明现在主数据源状态不是很理想啊
        if (currentFailedCount>=10&&!USE_BACKUP.get()){
            //就启用备用数据源
            USE_BACKUP.compareAndSet(false,true);
            System.out.println("主数据源繁忙，已切换数据源为备数据源，当前记录失败次数="+currentFailedCount+"，备用数据源标志位="+USE_BACKUP.get()+"，将使用备用数据源");
        }
    }

    /**
     * 获取数据源
     * @return
     */
    private ArrayBlockingQueue<LoginLogPO> getDatasource(){
        //如果主数据源队列未满，说明主数据库连接池资源充足，可以切回主数据源
        if (MAIN_DATASOURCE.isEmpty()){
            FAILED_COUNTER.set(0);
            USE_BACKUP.compareAndSet(true,false);
        }

        if (USE_BACKUP.get()){
            return BACKUP_DATASOURCE;
        }
        return MAIN_DATASOURCE;
    }

    @Override
    public int getBackupDatasourceSize() {
        return BACKUP_DATASOURCE.size();
    }
}
```
#### 持久层写好了，我们还要写一个Bean工厂才能通过静态方法获取一个单例的LoginLoggingDAO

```
/**
 * Bean工厂，用于获取日志记录的DAO
 * @author zhuzh
 * @date 2019.10.17
 */
public class LoginLogBeanFactory {
    private LoginLogBeanFactory(){}
    private static final Object locker = new Object();
    private static LoginLoggingDAO loginLoggingDAO;

    public static LoginLoggingDAO getInstance(){
        if (loginLoggingDAO == null){
            synchronized (locker){
                loginLoggingDAO = new MockLoginLoggingDAOImpl();
            }
        }
        return loginLoggingDAO;
    }

}
```

#### 当主数据源连接超时，我们要抛出一种特定的异常来做标记，便于handler识别当前状况
```
/**
 * 使用特定异常标明当前确实是数据源的问题
 * @author zhuzh
 * @date 2019.10.17
 */
public class DatasourceBusyException extends RuntimeException {
    public DatasourceBusyException(String message) {
        super(message);
    }
}
```

#### 线程的执行任务
```
/**
 * 记录登录日志的执行任务
 * @author zhuzh
 * @date 2019.10.17
 */
public class LoginLoggingTask implements Runnable {

    private LoginLogPO po;
    public LoginLoggingTask(LoginLogPO po1){
        po = po1;
    }

    @Override
    public void run() {
        LoginLoggingDAO dao = LoginLogBeanFactory.getInstance();
        dao.log(po);
    }

    @Override
    public String toString() {
        return "LoginLoggingTask{" +
                "po=" + po +
                '}';
    }
}
```

#### 自定义的线程

```
/**
 * 一个用来异步记录登录日志的线程
 * @author zhuzh
 * @date 2019.10.17
 */
public class LoginLoggerThread extends Thread {
    /**
     * 执行任务暂存
     */
    private Runnable task;

    /**
     * 此方法将runnable执行任务赋值给类的成员变量task，便于后续如果出现异常时可以重新执行任务
     * @param target
     */
    public LoginLoggerThread(Runnable target) {
        super(target);
        task = target;
    }

    public Runnable getTask() {
        return task;
    }
}
```

#### 和UncaughtExceptionHandler
```
/**
 * 一个自定义的线程任务异常处理器
 * @author zhuzh
 * @date 2019.10.17
 */
public class LoginLoggingUncaughtExceptionHandler implements Thread.UncaughtExceptionHandler {
    @Override
    public void uncaughtException(Thread t, Throwable e) {
        //如果确实是数据源的问题，我们再切换数据源，如果抛出了别的异常，暂不处理
        if (e instanceof DatasourceBusyException){
            LoginLoggingDAO dao = LoginLogBeanFactory.getInstance();
            dao.logFailed();
            LoginLoggerThread thread = (LoginLoggerThread)t;
            Runnable task = thread.getTask();
            System.out.println("第四步：线程执行异常，进入UncaughtExceptionHandler，任务重新丢回任务队列,task="+task.toString());
            AsynLogger.log(task);
        }
    }
}
```
#### 接下来我们再写一个异步日志处理类，接收日志记录到缓冲队列
```
/**
 * 一个登陆日志异步处理类
 * @author zhuzh
 * @date 2019.10.17
 */
public class AsynLogger {
    /**
     * 日志缓冲队列，当用户登录成功时，通过调用AsynLogger.log将日志放入缓冲队列
     */
    private static final ArrayBlockingQueue<Runnable> TASKS = new ArrayBlockingQueue<>(50);

    /**
     * 初始化50个用户登录日志
     */
    static {
        System.out.println("第一步：初始化50条登录日志数据放入[日志缓冲队列TASKS]");
        for (int i = 0; i < 50; i++) {
            TASKS.offer(new LoginLoggingTask(new LoginLogPO(i,"用户"+i,"2019-10-1"+i)));
        }
    }
    public static void log(Runnable loggingTask){
        TASKS.offer(loggingTask);
    }

    /**
     * 调用startLogging方法启动一个线程轮训缓冲队列
     */
    public static void startLogging(){
        Thread a = new Thread(()->{
        while (true){
            Runnable task = TASKS.poll();
            if (task==null){
                continue;
            }
            LoginLoggerThread thread = new LoginLoggerThread(task);
            thread.setUncaughtExceptionHandler(new LoginLoggingUncaughtExceptionHandler());
            thread.start();
        }});
        a.setDaemon(true);
        a.start();
        System.out.println("第二步：创建独立守护线程轮训[日志缓冲队列TASKS]，如果有数据，创建一个线程去处理，并且设置UncaughtExceptionHandler");
    }



}
```

#### 最后我们再写一个main方法，get it done!
```
/**
 * 测试功能主类
 * @author zhuzh
 * @date 2019.10.17
 */
public class UncaughtExceptionHandlerExample {

    public static void main(String[] args){
        AsynLogger.startLogging();
        try {
            Thread.sleep(10000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        System.out.println("第六步：所有任务执行完毕，备用数据源有"+ LoginLogBeanFactory.getInstance().getBackupDatasourceSize()+"条数据");
    }
}
```

### 输出结果

```
第一步：初始化50条登录日志数据放入[日志缓冲队列TASKS]
第二步：创建独立守护线程轮训[日志缓冲队列TASKS]，如果有数据，创建一个线程去处理，并且设置UncaughtExceptionHandler
第三步：将主数据源塞满，模拟主数据源连接池资源不足
第四步：线程执行异常，进入UncaughtExceptionHandler，任务重新丢回任务队列,task=LoginLoggingTask{po=LoginLogPO(id=33, username=用户33, createTime=2019-10-133)}
第四步：线程执行异常，进入UncaughtExceptionHandler，任务重新丢回任务队列,task=LoginLoggingTask{po=LoginLogPO(id=38, username=用户38, createTime=2019-10-138)}
第四步：线程执行异常，进入UncaughtExceptionHandler，任务重新丢回任务队列,task=LoginLoggingTask{po=LoginLogPO(id=5, username=用户5, createTime=2019-10-15)}
第四步：线程执行异常，进入UncaughtExceptionHandler，任务重新丢回任务队列,task=LoginLoggingTask{po=LoginLogPO(id=39, username=用户39, createTime=2019-10-139)}
第四步：线程执行异常，进入UncaughtExceptionHandler，任务重新丢回任务队列,task=LoginLoggingTask{po=LoginLogPO(id=34, username=用户34, createTime=2019-10-134)}
第四步：线程执行异常，进入UncaughtExceptionHandler，任务重新丢回任务队列,task=LoginLoggingTask{po=LoginLogPO(id=36, username=用户36, createTime=2019-10-136)}
第四步：线程执行异常，进入UncaughtExceptionHandler，任务重新丢回任务队列,task=LoginLoggingTask{po=LoginLogPO(id=47, username=用户47, createTime=2019-10-147)}
第四步：线程执行异常，进入UncaughtExceptionHandler，任务重新丢回任务队列,task=LoginLoggingTask{po=LoginLogPO(id=37, username=用户37, createTime=2019-10-137)}
第四步：线程执行异常，进入UncaughtExceptionHandler，任务重新丢回任务队列,task=LoginLoggingTask{po=LoginLogPO(id=8, username=用户8, createTime=2019-10-18)}
第四步：线程执行异常，进入UncaughtExceptionHandler，任务重新丢回任务队列,task=LoginLoggingTask{po=LoginLogPO(id=46, username=用户46, createTime=2019-10-146)}
主数据源繁忙，已切换数据源为备数据源，当前记录失败次数=10，备用数据源标志位=true，将使用备用数据源
第四步：线程执行异常，进入UncaughtExceptionHandler，任务重新丢回任务队列,task=LoginLoggingTask{po=LoginLogPO(id=23, username=用户23, createTime=2019-10-123)}
第四步：线程执行异常，进入UncaughtExceptionHandler，任务重新丢回任务队列,task=LoginLoggingTask{po=LoginLogPO(id=40, username=用户40, createTime=2019-10-140)}
第四步：线程执行异常，进入UncaughtExceptionHandler，任务重新丢回任务队列,task=LoginLoggingTask{po=LoginLogPO(id=13, username=用户13, createTime=2019-10-113)}
第四步：线程执行异常，进入UncaughtExceptionHandler，任务重新丢回任务队列,task=LoginLoggingTask{po=LoginLogPO(id=17, username=用户17, createTime=2019-10-117)}
第四步：线程执行异常，进入UncaughtExceptionHandler，任务重新丢回任务队列,task=LoginLoggingTask{po=LoginLogPO(id=45, username=用户45, createTime=2019-10-145)}
第四步：线程执行异常，进入UncaughtExceptionHandler，任务重新丢回任务队列,task=LoginLoggingTask{po=LoginLogPO(id=15, username=用户15, createTime=2019-10-115)}
第四步：线程执行异常，进入UncaughtExceptionHandler，任务重新丢回任务队列,task=LoginLoggingTask{po=LoginLogPO(id=44, username=用户44, createTime=2019-10-144)}
第四步：线程执行异常，进入UncaughtExceptionHandler，任务重新丢回任务队列,task=LoginLoggingTask{po=LoginLogPO(id=12, username=用户12, createTime=2019-10-112)}
第四步：线程执行异常，进入UncaughtExceptionHandler，任务重新丢回任务队列,task=LoginLoggingTask{po=LoginLogPO(id=31, username=用户31, createTime=2019-10-131)}
第四步：线程执行异常，进入UncaughtExceptionHandler，任务重新丢回任务队列,task=LoginLoggingTask{po=LoginLogPO(id=18, username=用户18, createTime=2019-10-118)}
第四步：线程执行异常，进入UncaughtExceptionHandler，任务重新丢回任务队列,task=LoginLoggingTask{po=LoginLogPO(id=35, username=用户35, createTime=2019-10-135)}
第五步：入库成功，入库成功的数据源为,currentDatasource=BACKUP_DATASOURCE
第四步：线程执行异常，进入UncaughtExceptionHandler，任务重新丢回任务队列,task=LoginLoggingTask{po=LoginLogPO(id=43, username=用户43, createTime=2019-10-143)}
第四步：线程执行异常，进入UncaughtExceptionHandler，任务重新丢回任务队列,task=LoginLoggingTask{po=LoginLogPO(id=29, username=用户29, createTime=2019-10-129)}
第四步：线程执行异常，进入UncaughtExceptionHandler，任务重新丢回任务队列,task=LoginLoggingTask{po=LoginLogPO(id=30, username=用户30, createTime=2019-10-130)}
第五步：入库成功，入库成功的数据源为,currentDatasource=BACKUP_DATASOURCE
第五步：入库成功，入库成功的数据源为,currentDatasource=BACKUP_DATASOURCE
第五步：入库成功，入库成功的数据源为,currentDatasource=BACKUP_DATASOURCE
第五步：入库成功，入库成功的数据源为,currentDatasource=BACKUP_DATASOURCE
第四步：线程执行异常，进入UncaughtExceptionHandler，任务重新丢回任务队列,task=LoginLoggingTask{po=LoginLogPO(id=19, username=用户19, createTime=2019-10-119)}
第四步：线程执行异常，进入UncaughtExceptionHandler，任务重新丢回任务队列,task=LoginLoggingTask{po=LoginLogPO(id=16, username=用户16, createTime=2019-10-116)}
第五步：入库成功，入库成功的数据源为,currentDatasource=BACKUP_DATASOURCE
第五步：入库成功，入库成功的数据源为,currentDatasource=BACKUP_DATASOURCE
第四步：线程执行异常，进入UncaughtExceptionHandler，任务重新丢回任务队列,task=LoginLoggingTask{po=LoginLogPO(id=7, username=用户7, createTime=2019-10-17)}
第五步：入库成功，入库成功的数据源为,currentDatasource=BACKUP_DATASOURCE
第五步：入库成功，入库成功的数据源为,currentDatasource=BACKUP_DATASOURCE
第五步：入库成功，入库成功的数据源为,currentDatasource=BACKUP_DATASOURCE
第四步：线程执行异常，进入UncaughtExceptionHandler，任务重新丢回任务队列,task=LoginLoggingTask{po=LoginLogPO(id=27, username=用户27, createTime=2019-10-127)}
第四步：线程执行异常，进入UncaughtExceptionHandler，任务重新丢回任务队列,task=LoginLoggingTask{po=LoginLogPO(id=11, username=用户11, createTime=2019-10-111)}
第五步：入库成功，入库成功的数据源为,currentDatasource=BACKUP_DATASOURCE
第四步：线程执行异常，进入UncaughtExceptionHandler，任务重新丢回任务队列,task=LoginLoggingTask{po=LoginLogPO(id=6, username=用户6, createTime=2019-10-16)}
第四步：线程执行异常，进入UncaughtExceptionHandler，任务重新丢回任务队列,task=LoginLoggingTask{po=LoginLogPO(id=4, username=用户4, createTime=2019-10-14)}
第四步：线程执行异常，进入UncaughtExceptionHandler，任务重新丢回任务队列,task=LoginLoggingTask{po=LoginLogPO(id=28, username=用户28, createTime=2019-10-128)}
第四步：线程执行异常，进入UncaughtExceptionHandler，任务重新丢回任务队列,task=LoginLoggingTask{po=LoginLogPO(id=3, username=用户3, createTime=2019-10-13)}
第五步：入库成功，入库成功的数据源为,currentDatasource=BACKUP_DATASOURCE
第四步：线程执行异常，进入UncaughtExceptionHandler，任务重新丢回任务队列,task=LoginLoggingTask{po=LoginLogPO(id=20, username=用户20, createTime=2019-10-120)}
第五步：入库成功，入库成功的数据源为,currentDatasource=BACKUP_DATASOURCE
第四步：线程执行异常，进入UncaughtExceptionHandler，任务重新丢回任务队列,task=LoginLoggingTask{po=LoginLogPO(id=2, username=用户2, createTime=2019-10-12)}
第四步：线程执行异常，进入UncaughtExceptionHandler，任务重新丢回任务队列,task=LoginLoggingTask{po=LoginLogPO(id=41, username=用户41, createTime=2019-10-141)}
第四步：线程执行异常，进入UncaughtExceptionHandler，任务重新丢回任务队列,task=LoginLoggingTask{po=LoginLogPO(id=9, username=用户9, createTime=2019-10-19)}
第四步：线程执行异常，进入UncaughtExceptionHandler，任务重新丢回任务队列,task=LoginLoggingTask{po=LoginLogPO(id=22, username=用户22, createTime=2019-10-122)}
第四步：线程执行异常，进入UncaughtExceptionHandler，任务重新丢回任务队列,task=LoginLoggingTask{po=LoginLogPO(id=1, username=用户1, createTime=2019-10-11)}
第四步：线程执行异常，进入UncaughtExceptionHandler，任务重新丢回任务队列,task=LoginLoggingTask{po=LoginLogPO(id=0, username=用户0, createTime=2019-10-10)}
第五步：入库成功，入库成功的数据源为,currentDatasource=BACKUP_DATASOURCE
第四步：线程执行异常，进入UncaughtExceptionHandler，任务重新丢回任务队列,task=LoginLoggingTask{po=LoginLogPO(id=25, username=用户25, createTime=2019-10-125)}
第五步：入库成功，入库成功的数据源为,currentDatasource=BACKUP_DATASOURCE
第四步：线程执行异常，进入UncaughtExceptionHandler，任务重新丢回任务队列,task=LoginLoggingTask{po=LoginLogPO(id=42, username=用户42, createTime=2019-10-142)}
第四步：线程执行异常，进入UncaughtExceptionHandler，任务重新丢回任务队列,task=LoginLoggingTask{po=LoginLogPO(id=48, username=用户48, createTime=2019-10-148)}
第四步：线程执行异常，进入UncaughtExceptionHandler，任务重新丢回任务队列,task=LoginLoggingTask{po=LoginLogPO(id=14, username=用户14, createTime=2019-10-114)}
第四步：线程执行异常，进入UncaughtExceptionHandler，任务重新丢回任务队列,task=LoginLoggingTask{po=LoginLogPO(id=49, username=用户49, createTime=2019-10-149)}
第四步：线程执行异常，进入UncaughtExceptionHandler，任务重新丢回任务队列,task=LoginLoggingTask{po=LoginLogPO(id=26, username=用户26, createTime=2019-10-126)}
第五步：入库成功，入库成功的数据源为,currentDatasource=BACKUP_DATASOURCE
第五步：入库成功，入库成功的数据源为,currentDatasource=BACKUP_DATASOURCE
第四步：线程执行异常，进入UncaughtExceptionHandler，任务重新丢回任务队列,task=LoginLoggingTask{po=LoginLogPO(id=24, username=用户24, createTime=2019-10-124)}
第四步：线程执行异常，进入UncaughtExceptionHandler，任务重新丢回任务队列,task=LoginLoggingTask{po=LoginLogPO(id=21, username=用户21, createTime=2019-10-121)}
第五步：入库成功，入库成功的数据源为,currentDatasource=BACKUP_DATASOURCE
第五步：入库成功，入库成功的数据源为,currentDatasource=BACKUP_DATASOURCE
第五步：入库成功，入库成功的数据源为,currentDatasource=BACKUP_DATASOURCE
第五步：入库成功，入库成功的数据源为,currentDatasource=BACKUP_DATASOURCE
第五步：入库成功，入库成功的数据源为,currentDatasource=BACKUP_DATASOURCE
第五步：入库成功，入库成功的数据源为,currentDatasource=BACKUP_DATASOURCE
第五步：入库成功，入库成功的数据源为,currentDatasource=BACKUP_DATASOURCE
第五步：入库成功，入库成功的数据源为,currentDatasource=BACKUP_DATASOURCE
第五步：入库成功，入库成功的数据源为,currentDatasource=BACKUP_DATASOURCE
第四步：线程执行异常，进入UncaughtExceptionHandler，任务重新丢回任务队列,task=LoginLoggingTask{po=LoginLogPO(id=10, username=用户10, createTime=2019-10-110)}
第四步：线程执行异常，进入UncaughtExceptionHandler，任务重新丢回任务队列,task=LoginLoggingTask{po=LoginLogPO(id=32, username=用户32, createTime=2019-10-132)}
第五步：入库成功，入库成功的数据源为,currentDatasource=BACKUP_DATASOURCE
第五步：入库成功，入库成功的数据源为,currentDatasource=BACKUP_DATASOURCE
第五步：入库成功，入库成功的数据源为,currentDatasource=BACKUP_DATASOURCE
第五步：入库成功，入库成功的数据源为,currentDatasource=BACKUP_DATASOURCE
第五步：入库成功，入库成功的数据源为,currentDatasource=BACKUP_DATASOURCE
第五步：入库成功，入库成功的数据源为,currentDatasource=BACKUP_DATASOURCE
第五步：入库成功，入库成功的数据源为,currentDatasource=BACKUP_DATASOURCE
第五步：入库成功，入库成功的数据源为,currentDatasource=BACKUP_DATASOURCE
第五步：入库成功，入库成功的数据源为,currentDatasource=BACKUP_DATASOURCE
第五步：入库成功，入库成功的数据源为,currentDatasource=BACKUP_DATASOURCE
第五步：入库成功，入库成功的数据源为,currentDatasource=BACKUP_DATASOURCE
第五步：入库成功，入库成功的数据源为,currentDatasource=BACKUP_DATASOURCE
第五步：入库成功，入库成功的数据源为,currentDatasource=BACKUP_DATASOURCE
第五步：入库成功，入库成功的数据源为,currentDatasource=BACKUP_DATASOURCE
第五步：入库成功，入库成功的数据源为,currentDatasource=BACKUP_DATASOURCE
第五步：入库成功，入库成功的数据源为,currentDatasource=BACKUP_DATASOURCE
第五步：入库成功，入库成功的数据源为,currentDatasource=BACKUP_DATASOURCE
第五步：入库成功，入库成功的数据源为,currentDatasource=BACKUP_DATASOURCE
第五步：入库成功，入库成功的数据源为,currentDatasource=BACKUP_DATASOURCE
第五步：入库成功，入库成功的数据源为,currentDatasource=BACKUP_DATASOURCE
第五步：入库成功，入库成功的数据源为,currentDatasource=BACKUP_DATASOURCE
第五步：入库成功，入库成功的数据源为,currentDatasource=BACKUP_DATASOURCE
第五步：入库成功，入库成功的数据源为,currentDatasource=BACKUP_DATASOURCE
第五步：入库成功，入库成功的数据源为,currentDatasource=BACKUP_DATASOURCE
第六步：所有任务执行完毕，备用数据源有50条数据

Process finished with exit code 0
```

>其实这种每次起一个线程去处理任务的方式是不正确的，**应该用线程池**，但是本文主要目的是讲解Thread的内部接口UncaughtExceptionHandler，后面我会用更好的方案来修改这个例子

本文的源代码在github上，可以到[这里下载](https://github.com/zhuxixi/learning-java-concurrency)