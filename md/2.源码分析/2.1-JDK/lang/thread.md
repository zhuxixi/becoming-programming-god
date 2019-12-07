## 写在前面
**这个类是一个非常重要的类，Java并发框架中的很多关键的Class源码多多少少都会用到Thread类的一些方法，如果你不懂那些方法的行为，你绝对无法完全看懂并发框架源码。所以这个类是必须要解读的，这是基础。**
## 基础知识
* [Runnable](https://juejin.im/post/5da1803a518825663830fc56)

## 1. Java Doc 正文
### 1.1 相关内容
Thread类是Runnable类的一个实现类。它还有一个子类，ForkJoinWorkerThread。
### 1.2 概述
Thread类代表程序中执行任务的一个线程。JVM允许程序同时运行多个线程并发执行任务。所有的线程都有优先级。高优先级的线程会优先执行。所有的线程也可以被标记为守护的，也就是守护线程。如果线程A创建了线程B，那么线程B的优先级会与A相同，如果A是一个守护线程，那么B也会继承此特性。

当JVM启动后，一般都会有一个非守护线程(某个类调用了main方法)。JVM会一直执行除非出现了以下情况：

* 系统调用exit方法，直接退出JVM
* 所有非守护线程都已经死掉，无论是正常执行还是抛出了异常

有两种创建线程的方法。

1. 创建一个Thread类的子类。这个子类需要重写Thread类的run方法。
举个栗子：
```
  class PrimeThread extends Thread {
       long minPrime;
       PrimeThread(long minPrime) {
           this.minPrime = minPrime;
       }

       public void run() {
           // compute primes larger than minPrime
            . . .
       }
   }
``` 
下面的代码会创建一个线程并且启动它。
```
PrimeThread p = new PrimeThread(143);
p.start();
```
2. 另一种创建线程的方式是去声明一个Runnable接口的实现类。这个实现类去实现Runnable的run方法。然后把这个实现类作为构造器的参数传给Thread，然后thread.start。代码如下：
```
class PrimeRun implements Runnable {
  long minPrime;
  PrimeRun(long minPrime) {
     this.minPrime = minPrime;
  }

  public void run() {
     // compute primes larger than minPrime
     . . .
  }
}
``` 
下面的代码会创建一个线程并且启动它。
```
PrimeRun p = new PrimeRun(143);
new Thread(p).start();
```
所有的线程都有一个名字，这样比较好识别。多个线程可能会有相同的名称。如果线程的名字没有被特别指定，那么创建线程时，系统会给你生成一个，比如 thread-0。
还有，你向Thread的构造方法传一个空的Runnable，会报NPE(空指针异常)。
>假如面试的时候面试官问你个问题，在Java中有几种创建线程的方式？看到这里你应该知道答案是什么了。**如果你不看官方的Java Doc，你就无法确定到底有几种方式。当然了，你可以去网上查帖子，但是谁也没办法给你保证网上那些人写的帖子一定是对的，一定要自己亲自去考量后才能给出肯定的答案，不然随时都有翻车的可能。**

### 1.3 内部类
#### 1.3.1 Java线程的六种状态 Thread.State 
Thread.State是Thread中的一个内部类，关于Thread.State我单开了一篇文章，加了一些样例代码加深理解和记忆，请[点击这里](https://juejin.im/post/5da7129a6fb9a04e3858578e)
#### 1.3.2 子线程的异常处理 Thread.UncaughtExceptionHandler 
Thread.UncaughtExceptionHandler也是一个内部类，关于Thread.UncaughtExceptionHandler我也单开了一篇文章，并且配合对应的场景去演示，有兴趣可以去读一读，跑跑代码加深记忆，详情请[点击这里](https://juejin.im/post/5da7d169f265da5b7637465a)
### 1.4 public 静态变量
这三个变量都是描述Thread的优先级
#### 1.4.1 public static final int MIN_PRIORITY = 1
线程的最小优先级 
#### 1.4.2 public static final int NORM_PRIORITY = 5  
线程的默认优先级
#### 1.4.3 public static final int MAX_PRIORITY = 10
线程的最大优先级
### 1.5 构造方法

#### 1.5.1 public Thread()
分配一个新的thread。这个构造器和Thread (null, null, gname)是一个效果,gname是一个新生成的线程名。线程名的生成规则："Thread-"+n，n为整数。
#### 1.5.2 public Thread(Runnable target)
target -  传递一个执行任务，当调用thread.start之后，target.run方法会被执行，如果传空，那么这个线程就什么也不做。
#### 1.5.3 public Thread(ThreadGroup group,Runnable target)
group - 可以指定这个线程的线程组,如果线程组为空，会使用current thread的线程组
#### 1.5.4 public Thread(String name)
name - 指定线程名
#### 1.5.5 public Thread(ThreadGroup group,String name)
不多说了
#### 1.5.6 public Thread(Runnable target,String name)
不多说了
#### 1.5.7 public Thread(ThreadGroup group,Runnable target,String name)
不多说了
#### 1.5.8 public Thread(ThreadGroup group,Runnable target,String name,long stackSize)
这个得说一下
重点关注stackSize这个参数。stack size是这个线程的线程栈大小，它是一个近似值，单位是byte。这个参数行为，主要取决于运行jvm的平台。

在一些平台，将stackSize调大可能会允许线程达到更深的递归深度。类似的，将stackSize调小可以节省内存空间，这样同时存活的线程数会更高。stackSize参数、线程递归深度和并发数量的关系主要取决于操作系统是如何实现的。在某些平台，设置stackSize可能不起作用。

JVM可以将stackSize的设置当做一种建议。如果你设置一个特别小的值，就是那种根本就不合理的值，JVM会将这个值替换为操作系统默认的最低值，相反，你设置一个特别大的(比如几TB)，JVM会替换成默认的最大值。同样，JVM可以对你设置的值做一些权衡，然后适当的调高和调低(或者完全无视这个值)。

**如果为stackSize参数指定一个0值，等于忽略这个值，这样这个构造方法和Thread(ThreadGroup, Runnable, String)是等价的。**


如果你要调整这个值，那么你最好先做做测试。windows和linux系统的thread stack size可能不一样，如果要对stack size做调整，务必保证你的测试覆盖了所有程序可能运行的平台，否则可能就会踩坑。

Parameters:  
group - 线程组  
target - 执行任务  
name - 线程名  
stackSize - 线程栈大小，单位是byte。如果设置为0等于忽略了这个参数，走默认值
### 1.6 方法
#### 1.6.1 public static Thread currentThread()
返回当前正在执行的线程的引用。
#### 1.6.2 public static void yield()
调用此方法会告诉线程调度器当前线程可以放弃对CPU的使用。调度器也可以忽略这个请求。开发这个方法的目的是尝试改善多线程协作时某些线程对CPU占用偏高的问题。这个方法的使用应该做好测试来保证这方法真的按照预期执行。

这个方法极少会被使用。不过它在debug或test时候可能比较有用，它有助于帮助重现bug。

#### 1.6.3 public static void sleep(long millis)throws InterruptedException
调用此方法会导致当前正在执行的线程睡眠一段时间，实际睡眠时间取决于调度器和系统计时器。这个线程在睡眠时依然会持有锁。  
Parameters:    
millis - 睡眠时间，单位是毫秒  
Throws:  
IllegalArgumentException - 毫秒参数如果传了负数就抛这个异常  
InterruptedException - 如果线程在睡眠时其他线程调用了这个线程的interrupt方法，会让这个线程抛出这个异常，线程可以根据自己的逻辑去做一些处理

#### 1.6.4 public static void sleep(long millis,int nanos) throws InterruptedException
调用此方法会导致当前正在执行的线程睡眠指定的毫秒+纳秒数，实际睡眠时间取决于调度器和系统计时器。这个线程在睡眠时依然会持有锁。  
Parameters:  
millis - 睡眠的毫秒数  
nanos - 0-999999 多余的纳秒数  
Throws:  
IllegalArgumentException - 如果毫秒参数是负数,或者纳秒数>0 或 < 999999  
InterruptedException - 如果线程在睡眠时其他线程调用了这个线程的interrupt方法，会让这个线程抛出这个异常，线程可以根据自己的逻辑去做一些处理
#### 1.6.5 protected Object clone()throws CloneNotSupportedException
克隆一个线程没有什么意义，其实就是起一个新线程。**你可以声明一个自定义的线程来继承Thread并重写clone方法，即使你这样做了，你调用这个方法它总会抛出CloneNotSupportedExeception。所以这个方法可以忽略了，想都不要想。**  
Overrides:  
方法要在子类中重写  
Returns:  
这个实例的一个克隆，其实一直会抛出异常，不会返回的  
Throws:  
CloneNotSupportedException - 永远都抛出这个异常
#### 1.6.6 public void start()
让这个线程开始执行，JVM会调用这个线程的run方法。
这个方法的调用会导致当前有两个线程并发执行：一个是当前线程，另一个是你声明并调用了start()的线程
一个线程不要start两次，你调用了也不会执行两次，会抛出异常。

Throws:  
IllegalThreadStateException - 如果线程已经start了，你再调start就抛出异常
#### 1.6.7 public void run()
如果这个线程使用一个特定的Runnable对象来初始化，那么最终这个runnable对象的run方法会被执行。否则这个方法啥也不做。Thread的子类应该重写这个方法。
#### 1.6.8 public void interrupt()
打断这个线程。如果这个线程被wait()、join()、sleep()方法阻塞，那么这个线程的中断标志会被重置，而且收到一个InterruptedException。如果这个线程被一个Selector阻塞，那么它收到interrupt方法时这个interrrupt状态会被设置，而且会立即返回，可能会带着一个非空值，就像select方法正常返回一样。打断一个不是alive状态的线程是不会有效果的。
#### 1.6.9 public static boolean interrupted()
检测当前线程是否已经被interrupt。这个线程的interrupt状态会被这个方法清除。也就是说，如果你调用这个方法两次，那么第二次一定会返回false，除非第二次调用interrupt之前，这个线程又被interrupt了。

Returns:  
如果线程被interrupt过，那么返回true，否则返回false  
See Also:  
isInterrupted()
#### 1.6.10 public boolean isInterrupted()
检测这个线程是否被interrupt。中断状态在调用此方法后不会有影响。

Returns:  
如果线程被interrupt过，那么返回true，否则返回false  
See Also:  
isInterrupted()
#### 1.6.11 public final boolean isAlive()
检验这个线程是否是活跃的。一个线程已经被start而且还没死亡的话，那就说这个线程是活的。

Returns:
活的就返回true。死的就返回false
#### 1.6.12 public final void setPriority(int newPriority)
修改这个线程的优先级。

Parameters:  
newPriority - 新的优先级  
Throws:
IllegalArgumentException - 如果优先级不再这个范围 [MIN_PRIORITY,MAX_PRIORITY]  
SecurityException - 如果当前线程无法修改这个线程

#### 1.6.13 public final int getPriority()
返回线程的优先级  

#### 1.6.14 public final void setName(String name)
修改这个线程的名称
#### 1.6.15 public final String getName()
获取这个线程的名称
#### 1.6.16 public final ThreadGroup getThreadGroup()
获取这个线程所属的线程组。如果这个线程已经死掉了，那么这个方法会返回null。
#### 1.6.17 public static int activeCount()
返回一个预估值，这个值代表当前线程组和线程子组的所有活跃线程数，**只是预估值**。递归的迭代所有这个线程所属线程组与其线程子组。  
之所以是一个预估的值，是因为这个活跃线程数量可能会动态的变化。**这个方法主要用于监控和debugg。**
#### 1.6.18 public static int enumerate(Thread[] tarray)
将当前线程的线程和子线程组所有的活跃线程都拷贝到入参的数组中。这个方法其实就是调用当前线程的ThreadGroup.enumerate(Thread[])。

一般你得调用activeCount方法统计一下你到底需要多大的array，如果入参数组长度比较小，而实际线程多，多余的线程会被忽略。

**这个方法主要用于监控和debugg。**


#### 1.6.19 public final void join(long millis) throws InterruptedException
在这个线程死亡之前等待入参的毫秒数，也就是线程执行完了，但是还要再等一下。如果参数传0就永远等待。

#### 1.6.20 public final void join(long millis,int nanos)throws InterruptedException
等待额外的纳秒
#### 1.6.21 public final void join()throws InterruptedException
就是join(0)
#### 1.6.22 public static void dumpStack()
打印当前线程的栈信息。这个方法只用于debug
#### 1.6.23 public final void setDaemon(boolean on)
标记这个线程是一个守护线程还是一个用户线程。JVM中只有守护线程时就会退出。**这个方法必须在thread.start方法调用前使用**。
#### 1.6.24 public final boolean isDaemon()
检验这个线程是不是守护线程
#### 1.6.25 public final void checkAccess()
判断当前的线程是否有权限去修改这个线程。没权限就抛出异常
#### 1.6.26 public ClassLoader getContextClassLoader()
返回这个线程的ClassLoader。
#### 1.6.27 public void setContextClassLoader(ClassLoader cl)
设置这个classloader
#### 1.6.28 public static boolean holdsLock(Object obj)
判断当前线程是否持有某个特定对象的锁。
#### 1.6.29 public StackTraceElement[] getStackTrace()
返回一个堆栈信息的数组代表这个线程转储。如果在线程start之前就调用这个方法；已经start但是还没有真正的运行，CPU没调度；已经结束，那么会返回一个长度为0的数组。如果返回的数组长度不为0，那么数组的第一个元素就是线程最远执行过的方法，数组的最后一个元素是最近执行过的方法调用。
#### 1.6.30 public static Map<Thread,StackTraceElement[]> getAllStackTraces()
返回一个map，包含了所有活跃线程和他们对应的StackTrace。这些堆栈信息都是快照，也就是说堆栈信息一直在变化，这个只是某个时刻的信息。
#### 1.6.31 public long getId()
返回这个线程的id。这个id是一个是一个正的long数字，当线程被创建时自动生成。这个id是唯一的，并且在线程消亡之前是无法更改的。但是当一个线程死掉了，这个线程的id可能会被其他线程使用。
#### 1.6.32 public Thread.State getState()
返回这个线程的状态。这个方法一般用于监控系统状态，不是为了同步控制。
#### 1.6.33 public static void setDefaultUncaughtExceptionHandler(Thread.UncaughtExceptionHandler eh)
设置默认的UncaughExceptionHandler。还记得UncauthExceptionHandler吧，在线程执行任务期间抛出异常并且没有被catch住时，会将Thread 和 异常对象传递给这个handler做一些后续的处理。
如果这个线程没有其他的handler，那么就会使用这个默认的handler。具体的可以看我另外一篇帖子，链接在上面。
#### 1.6.34 public static Thread.UncaughtExceptionHandler getDefaultUncaughtExceptionHandler()
返回这个defaultHandler，如果没有默认的，返回null。
#### 1.6.35 public Thread.UncaughtExceptionHandler getUncaughtExceptionHandler()
不多说了
#### 1.6.36 public void setUncaughtExceptionHandler(Thread.UncaughtExceptionHandler eh)
不多说了

## 2. Thread源码解读
为了方便阅读，我把Thread类的源码发到了掘金上，[点击这里](https://juejin.im/post/5db5a20df265da4d31073726)可以查看原版源码阅读以下英文注释

```
package java.lang;

import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.security.AccessController;
import java.security.AccessControlContext;
import java.security.PrivilegedAction;
import java.util.Map;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.locks.LockSupport;
import sun.nio.ch.Interruptible;
import sun.reflect.CallerSensitive;
import sun.reflect.Reflection;
import sun.security.util.SecurityConstants;

public class Thread implements Runnable {
    /* Make sure registerNatives is the first thing <clinit> does. */
    private static native void registerNatives();
    static {
        registerNatives();
    }
    //线程名称，通过setName，init方法可以设置
    private volatile String name;
    //线程的优先级
    private int            priority;
    //这个字段源码没有用到，具体有什么作用不清楚
    private Thread         threadQ;
    //这个字段源码没有用到，具体有什么作用不清楚
    private long           eetop;
    //这个字段源码没有用到，具体有什么作用不清楚
    private boolean     single_step;
    //是否是守护线程
    private boolean     daemon = false;

    //这个字段源码没有用到，具体有什么作用不清楚
    private boolean     stillborn = false;

    //线程要执行的任务
    private Runnable target;

    //这个线程的线程组
    private ThreadGroup group;

    //这个线程的classLoader
    private ClassLoader contextClassLoader;

    //Java的安全管理器 具体有什么用，我还不清楚，后续搞清楚了会更新
    private AccessControlContext inheritedAccessControlContext;

    //如果new 线程时没有指定名称，会通过这个变量和下个方法生产一个序号作为线程name
    private static int threadInitNumber;
    private static synchronized int nextThreadNum() {
        return threadInitNumber++;
    }

    //这个线程的ThreadLocal对象，有关ThreadLocal我们单开帖子去讲
    ThreadLocal.ThreadLocalMap threadLocals = null;

    //这个具体什么作用不清楚
    ThreadLocal.ThreadLocalMap inheritableThreadLocals = null;

    //线程栈大小
    private long stackSize;

    //这个字段源码没有用到，具体有什么作用不清楚，应该是JVM对线程的操作
    private long nativeParkEventPointer;

    //线程id
    private long tid;

    //为了生成有序的线程ID
    private static long threadSeqNumber;

    //线程的状态
    private volatile int threadStatus = 0;

    //为了生成有序的线程ID
    private static synchronized long nextThreadID() {
        return ++threadSeqNumber;
    }

    /**
     * 这个和Lock框架有关，后续lock框架搞完再补充
     * The argument supplied to the current call to
     * java.util.concurrent.locks.LockSupport.park.
     * Set by (private) java.util.concurrent.locks.LockSupport.setBlocker
     * Accessed using java.util.concurrent.locks.LockSupport.getBlocker
     */
    volatile Object parkBlocker;

    /* The object in which this thread is blocked in an interruptible I/O
     * operation, if any.  The blocker's interrupt method should be invoked
     * after setting this thread's interrupt status.
     */
    private volatile Interruptible blocker;
    private final Object blockerLock = new Object();

    /* Set the blocker field; invoked via sun.misc.SharedSecrets from java.nio code
     */
    void blockedOn(Interruptible b) {
        synchronized (blockerLock) {
            blocker = b;
        }
    }

    /**
     * 最小优先级
     */
    public final static int MIN_PRIORITY = 1;

   /**
     * 默认优先级
     */
    public final static int NORM_PRIORITY = 5;

    /**
     * 最大优先级
     */
    public final static int MAX_PRIORITY = 10;

    /**
     * native方法，返回当前线程的引用
     * 什么是native方法，native方法就是jdk里面一些使用C++或C实现的较底层的方法
     */
    public static native Thread currentThread();

    /**
     * native方法，表示当前线程放弃CPU资源，暂停执行
     */
    public static native void yield();

    /**
     * native方法，sleep一会儿
     */
    public static native void sleep(long millis) throws InterruptedException;

    /**
     * sleep一会儿，但是会判断一下超时时间是否有效
     */
    public static void sleep(long millis, int nanos)
    throws InterruptedException {
        if (millis < 0) {
            throw new IllegalArgumentException("timeout value is negative");
        }

        if (nanos < 0 || nanos > 999999) {
            throw new IllegalArgumentException(
                                "nanosecond timeout value out of range");
        }

        if (nanos >= 500000 || (nanos != 0 && millis == 0)) {
            millis++;
        }

        sleep(millis);
    }

    /**
     * 初始化线程，其实就是调用最全的那个init
     */
    private void init(ThreadGroup g, Runnable target, String name,
                      long stackSize) {
        init(g, target, name, stackSize, null, true);
    }

    /**
     * 初始化一个线程
     *
     * @param 线程组
     * @param 执行任务
     * @param 线程名称
     * @param 线程栈大小
     * @param 安全控制器
     * @param 继承的ThreadLocal，这个参数具体作用还需要再看一下
     */
    private void init(ThreadGroup g, Runnable target, String name,
                      long stackSize, AccessControlContext acc,
                      boolean inheritThreadLocals) {
        if (name == null) {
            throw new NullPointerException("name cannot be null");
        }

        this.name = name;
        //拿到当前线程引用，当前线程正在创建一个子线程，当前线程就是父线程
        Thread parent = currentThread();
        SecurityManager security = System.getSecurityManager();
        //如果不线程组对象
        if (g == null) {
            //检查SecurityManager是否为空，如果不为空，使用SecurityManager的线程组
            if (security != null) {
                g = security.getThreadGroup();
            }

            //如果没有SecurityManager，就是用父线程的线程组
            if (g == null) {
                g = parent.getThreadGroup();
            }
        }

        //检查是否有权限
        g.checkAccess();

        //检查是否有权限
        if (security != null) {
            if (isCCLOverridden(getClass())) {
                security.checkPermission(SUBCLASS_IMPLEMENTATION_PERMISSION);
            }
        }
        //线程组将这个线程加入到未启动
        g.addUnstarted();
        
        this.group = g;
        //如果父线程是守护线程，那么当前线程也会成为守护线程
        this.daemon = parent.isDaemon();
        //优先级使用父线程的优先级
        this.priority = parent.getPriority();
        if (security == null || isCCLOverridden(parent.getClass()))
            this.contextClassLoader = parent.getContextClassLoader();
        else
            this.contextClassLoader = parent.contextClassLoader;
        this.inheritedAccessControlContext =
                acc != null ? acc : AccessController.getContext();
        this.target = target;
        setPriority(priority);
        if (inheritThreadLocals && parent.inheritableThreadLocals != null)
            this.inheritableThreadLocals =
                ThreadLocal.createInheritedMap(parent.inheritableThreadLocals);
        //设置线程栈
        this.stackSize = stackSize;

        //调用静态私有方法生成一个线程ID
        tid = nextThreadID();
    }

    /**
     * 看代码，永远都会抛出一个CloneNotSupportedException异常，Thread类不支持克隆
     */
    @Override
    protected Object clone() throws CloneNotSupportedException {
        throw new CloneNotSupportedException();
    }

    /**
     * 无参的构造方法，不多说了，上面已经很详细了
     * 线程名使用nextThreadNum来设置
     */
    public Thread() {
        init(null, null, "Thread-" + nextThreadNum(), 0);
    }

    /**
     * 不多说了
     */
    public Thread(Runnable target) {
        init(null, target, "Thread-" + nextThreadNum(), 0);
    }

    /**
     * 传一个安全控制器
     */
    Thread(Runnable target, AccessControlContext acc) {
        init(null, target, "Thread-" + nextThreadNum(), 0, acc, false);
    }

    /**
     * 不多说
     */
    public Thread(ThreadGroup group, Runnable target) {
        init(group, target, "Thread-" + nextThreadNum(), 0);
    }

    /**
     * 不多说
     */
    public Thread(String name) {
        init(null, null, name, 0);
    }

    /**
     * 不多说
     */
    public Thread(ThreadGroup group, String name) {
        init(group, null, name, 0);
    }

    /**
     * 不多说
     */
    public Thread(Runnable target, String name) {
        init(null, target, name, 0);
    }

    /**
     * 不多说
     */
    public Thread(ThreadGroup group, Runnable target, String name) {
        init(group, target, name, 0);
    }

    /**
     * 不多说
     */
    public Thread(ThreadGroup group, Runnable target, String name,
                  long stackSize) {
        init(group, target, name, stackSize);
    }

    /**
     * 启动线程，这个方法是个同步的方法
     */
    public synchronized void start() {
        /**
         * 如果不是刚new出来的线程，抛异常
         */
        if (threadStatus != 0)
            throw new IllegalThreadStateException();

        // 线程组添加此线程，然后unStarted可以减少，具体看ThreadGroup源码
        group.add(this);
        
        boolean started = false;
        try {
            //调用原生方法启动线程
            start0();
            started = true;
        } finally {
            try {
                if (!started) {
                    group.threadStartFailed(this);
                }
            } catch (Throwable ignore) {
                //啥也不干
            }
        }
    }
    //native方法，这个方法才是真正启动线程的方法
    private native void start0();

    /**
     * 不多说，这个是实现Runnable接口的方法
     */
    @Override
    public void run() {
        if (target != null) {
            target.run();
        }
    }

    /**
     * 这个方法是JVM调用的，用来做一些清理工作
     */
    private void exit() {
        if (group != null) {
            group.threadTerminated(this);
            group = null;
        }
        //全都给置空
        target = null;
        threadLocals = null;
        inheritableThreadLocals = null;
        inheritedAccessControlContext = null;
        blocker = null;
        uncaughtExceptionHandler = null;
    }

    /**
     * 这个方法过时了，最好不要用，本文也不会去分析
     */
    @Deprecated
    public final void stop() {
        SecurityManager security = System.getSecurityManager();
        if (security != null) {
            checkAccess();
            if (this != Thread.currentThread()) {
                security.checkPermission(SecurityConstants.STOP_THREAD_PERMISSION);
            }
        }
        // A zero status value corresponds to "NEW", it can't change to
        // not-NEW because we hold the lock.
        if (threadStatus != 0) {
            resume(); // Wake up thread if it was suspended; no-op otherwise
        }

        // The VM can handle all thread states
        stop0(new ThreadDeath());
    }

    /**
     * 一样不要用
     */
    @Deprecated
    public final synchronized void stop(Throwable obj) {
        throw new UnsupportedOperationException();
    }

    /**
     * 打断线程
     */
    public void interrupt() {
        //判断权限
        if (this != Thread.currentThread())
            checkAccess();
        //这个锁还不太清楚，要去研究一下Lock
        synchronized (blockerLock) {
            Interruptible b = blocker;
            if (b != null) {
                //native方法
                interrupt0();           // Just to set the interrupt flag
                b.interrupt(this);
                return;
            }
        }
        //native方法
        interrupt0();
    }

    /**
     * 静态方法，判断当前线程是否被打断，清除状态
     */
    public static boolean interrupted() {
        return currentThread().isInterrupted(true);
    }

    /**
     * 判断是否打断，不清除状态
     */
    public boolean isInterrupted() {
        return isInterrupted(false);
    }

    /**
     * native方法，判断线程是否被打断了。
     * 这个线程的interrupted 状态是否会被重置主要取决于这个参数
     */
    private native boolean isInterrupted(boolean ClearInterrupted);

    /**
     * 不要用这个方法，已经过时了
     */
    @Deprecated
    public void destroy() {
        throw new NoSuchMethodError();
    }

    /**
     * native方法，判断这个线程是否还活着
     */
    public final native boolean isAlive();

    /**
     * 过时了，不要用
     */
    @Deprecated
    public final void suspend() {
        checkAccess();
        suspend0();
    }

    /**
     * 过时了，不要用
     */
    @Deprecated
    public final void resume() {
        checkAccess();
        resume0();
    }

    /**
     * 设置优先级
     */
    public final void setPriority(int newPriority) {
        ThreadGroup g;
        checkAccess();
        if (newPriority > MAX_PRIORITY || newPriority < MIN_PRIORITY) {
            throw new IllegalArgumentException();
        }
        //如果线程组不为空
        if((g = getThreadGroup()) != null) {
            if (newPriority > g.getMaxPriority()) {
                newPriority = g.getMaxPriority();
            }
            //这也是native方法
            setPriority0(priority = newPriority);
        }
    }

    /**
     * 获取优先级
     */
    public final int getPriority() {
        return priority;
    }

    /**
     * 设置线程的名称
     */
    public final synchronized void setName(String name) {
        checkAccess();
        if (name == null) {
            throw new NullPointerException("name cannot be null");
        }

        this.name = name;
        if (threadStatus != 0) {
            //这是个native方法
            setNativeName(name);
        }
    }

    /**
     * 返回线程名
     */
    public final String getName() {
        return name;
    }

    /**
     * 获取这个线程的线程组
     */
    public final ThreadGroup getThreadGroup() {
        return group;
    }

    /**
     * 获取当前线程所在线程组的活跃线程数，这个值是动态的
     */
    public static int activeCount() {
        return currentThread().getThreadGroup().activeCount();
    }

    /**
     * 不多说了，详细的说明单开ThreadGroup去讲
     */
    public static int enumerate(Thread tarray[]) {
        return currentThread().getThreadGroup().enumerate(tarray);
    }

    /**
     * 过期了，不要用
     */
    @Deprecated
    public native int countStackFrames();

    /**
     * 线程任务执行完之后等待一段时间，核心方法是native的wait
     */
    public final synchronized void join(long millis)
    throws InterruptedException {
        long base = System.currentTimeMillis();
        long now = 0;

        if (millis < 0) {
            throw new IllegalArgumentException("timeout value is negative");
        }

        if (millis == 0) {
            while (isAlive()) {
                //native方法，传0就是永远等下去
                wait(0);
            }
        } else {
            while (isAlive()) {
                long delay = millis - now;
                if (delay <= 0) {
                    break;
                }
                //等一会儿
                wait(delay);
                now = System.currentTimeMillis() - base;
            }
        }
    }

    /**
     * 理论上是毫秒+指定的纳秒数，但是好像是骗人的
     */
    public final synchronized void join(long millis, int nanos)
    throws InterruptedException {

        if (millis < 0) {
            throw new IllegalArgumentException("timeout value is negative");
        }

        if (nanos < 0 || nanos > 999999) {
            throw new IllegalArgumentException(
                                "nanosecond timeout value out of range");
        }
        //这里纳秒大于500000等于是+了一毫秒，否则按照毫秒去Wait
        if (nanos >= 500000 || (nanos != 0 && millis == 0)) {
            millis++;
        }

        join(millis);
    }

    /**
     * 永远的join下去
     */
    public final void join() throws InterruptedException {
        join(0);
    }

    /**
     * 打印堆栈，只有debugg的时候使用，平时别用
     */
    public static void dumpStack() {
        new Exception("Stack trace").printStackTrace();
    }

    /**
     * 设置守护线程标志，记得是启动前设置
     */
    public final void setDaemon(boolean on) {
        checkAccess();
        if (isAlive()) {
            throw new IllegalThreadStateException();
        }
        daemon = on;
    }

    /**
     * 判断线程是否是守护线程
     */
    public final boolean isDaemon() {
        return daemon;
    }

    /**
     * 检查权限，后面会单开一个安全管理器的帖子去讲，日常很少用到
     */
    public final void checkAccess() {
        SecurityManager security = System.getSecurityManager();
        if (security != null) {
            security.checkAccess(this);
        }
    }

    /**
     * 打印线程的字符串表示
     */
    public String toString() {
        ThreadGroup group = getThreadGroup();
        if (group != null) {
            return "Thread[" + getName() + "," + getPriority() + "," +
                           group.getName() + "]";
        } else {
            return "Thread[" + getName() + "," + getPriority() + "," +
                            "" + "]";
        }
    }

    /**
     * 获取ClassLoader，注意CallerSensitive这个注解，调用者必须有权限
     * 涉及到ClassLoader的一些知识，后续单开帖子去讲
     */
    @CallerSensitive
    public ClassLoader getContextClassLoader() {
        if (contextClassLoader == null)
            return null;
        SecurityManager sm = System.getSecurityManager();
        if (sm != null) {
            ClassLoader.checkClassLoaderPermission(contextClassLoader,
                                                   Reflection.getCallerClass());
        }
        return contextClassLoader;
    }

    /**
     * 设置这个ClassLoader
     */
    public void setContextClassLoader(ClassLoader cl) {
        SecurityManager sm = System.getSecurityManager();
        if (sm != null) {
            sm.checkPermission(new RuntimePermission("setContextClassLoader"));
        }
        contextClassLoader = cl;
    }

    /**
     * native方法，判断是否有锁
     */
    public static native boolean holdsLock(Object obj);
    
    //栈帧数组
    private static final StackTraceElement[] EMPTY_STACK_TRACE
        = new StackTraceElement[0];

    /**
     * 获取线程栈帧
     */
    public StackTraceElement[] getStackTrace() {
        if (this != Thread.currentThread()) {
            SecurityManager security = System.getSecurityManager();
            if (security != null) {
                security.checkPermission(
                    SecurityConstants.GET_STACK_TRACE_PERMISSION);
            }
            
            if (!isAlive()) {
                return EMPTY_STACK_TRACE;
            }
            //dumpThreads这又是个native方法，其实是它来导出线程栈的
            StackTraceElement[][] stackTraceArray = dumpThreads(new Thread[] {this});
            StackTraceElement[] stackTrace = stackTraceArray[0];
            // 上面调用isAlive虽然返回true，但是可能后面线程死掉了，就会导致
            //stackTrace为null
            if (stackTrace == null) {
                stackTrace = EMPTY_STACK_TRACE;
            }
            return stackTrace;
        } else {
            // Don't need JVM help for current thread
            return (new Exception()).getStackTrace();
        }
    }

    /**
     * 获取所有线程的ThreadDump
     */
    public static Map<Thread, StackTraceElement[]> getAllStackTraces() {
        SecurityManager security = System.getSecurityManager();
        if (security != null) {
            security.checkPermission(
                SecurityConstants.GET_STACK_TRACE_PERMISSION);
            security.checkPermission(
                SecurityConstants.MODIFY_THREADGROUP_PERMISSION);
        }

        // native方法，获取所有活跃线程
        Thread[] threads = getThreads();
        StackTraceElement[][] traces = dumpThreads(threads);
        Map<Thread, StackTraceElement[]> m = new HashMap<>(threads.length);
        for (int i = 0; i < threads.length; i++) {
            StackTraceElement[] stackTrace = traces[i];
            if (stackTrace != null) {
                m.put(threads[i], stackTrace);
            }
        }
        return m;
    }


    private static final RuntimePermission SUBCLASS_IMPLEMENTATION_PERMISSION =
                    new RuntimePermission("enableContextClassLoaderOverride");

    /** cache of subclass security audit results */
    /* Replace with ConcurrentReferenceHashMap when/if it appears in a future
     * release */
    private static class Caches {
        /** cache of subclass security audit results */
        static final ConcurrentMap<WeakClassKey,Boolean> subclassAudits =
            new ConcurrentHashMap<>();

        /** queue for WeakReferences to audited subclasses */
        static final ReferenceQueue<Class<?>> subclassAuditsQueue =
            new ReferenceQueue<>();
    }

    /**
     * Verifies that this (possibly subclass) instance can be constructed
     * without violating security constraints: the subclass must not override
     * security-sensitive non-final methods, or else the
     * "enableContextClassLoaderOverride" RuntimePermission is checked.
     */
    private static boolean isCCLOverridden(Class<?> cl) {
        if (cl == Thread.class)
            return false;

        processQueue(Caches.subclassAuditsQueue, Caches.subclassAudits);
        WeakClassKey key = new WeakClassKey(cl, Caches.subclassAuditsQueue);
        Boolean result = Caches.subclassAudits.get(key);
        if (result == null) {
            result = Boolean.valueOf(auditSubclass(cl));
            Caches.subclassAudits.putIfAbsent(key, result);
        }

        return result.booleanValue();
    }

    /**
     * Performs reflective checks on given subclass to verify that it doesn't
     * override security-sensitive non-final methods.  Returns true if the
     * subclass overrides any of the methods, false otherwise.
     */
    private static boolean auditSubclass(final Class<?> subcl) {
        Boolean result = AccessController.doPrivileged(
            new PrivilegedAction<Boolean>() {
                public Boolean run() {
                    for (Class<?> cl = subcl;
                         cl != Thread.class;
                         cl = cl.getSuperclass())
                    {
                        try {
                            cl.getDeclaredMethod("getContextClassLoader", new Class<?>[0]);
                            return Boolean.TRUE;
                        } catch (NoSuchMethodException ex) {
                        }
                        try {
                            Class<?>[] params = {ClassLoader.class};
                            cl.getDeclaredMethod("setContextClassLoader", params);
                            return Boolean.TRUE;
                        } catch (NoSuchMethodException ex) {
                        }
                    }
                    return Boolean.FALSE;
                }
            }
        );
        return result.booleanValue();
    }

    private native static StackTraceElement[][] dumpThreads(Thread[] threads);
    private native static Thread[] getThreads();

    //获取id
    public long getId() {
        return tid;
    }

    /**
     * 内部类，线程状态
     * 详细说明可以看我单开的帖子
     */
    public enum State {
        
        NEW,

        
        RUNNABLE,

        
        BLOCKED,

        
        WAITING,

        
        TIMED_WAITING,

        
        TERMINATED;
    }

    /**
     * 获取线程状态
     */
    public State getState() {
        // get current thread state
        return sun.misc.VM.toThreadState(threadStatus);
    }

    /**
     * 线程异常处理器
     */
    @FunctionalInterface
    public interface UncaughtExceptionHandler {
        /**
         * 线程执行任务过程中出现未检查异常时，JVM会调用这个方法
         */
        void uncaughtException(Thread t, Throwable e);
    }

    //处理器
    private volatile UncaughtExceptionHandler uncaughtExceptionHandler;

    //默认的处理器
    private static volatile UncaughtExceptionHandler defaultUncaughtExceptionHandler;

    /**
     * 设置默认的处理器
     */
    public static void setDefaultUncaughtExceptionHandler(UncaughtExceptionHandler eh) {
        SecurityManager sm = System.getSecurityManager();
        if (sm != null) {
            sm.checkPermission(
                new RuntimePermission("setDefaultUncaughtExceptionHandler")
                    );
        }

         defaultUncaughtExceptionHandler = eh;
     }

    /**
     * 获取默认的处理器
     */
    public static UncaughtExceptionHandler getDefaultUncaughtExceptionHandler(){
        return defaultUncaughtExceptionHandler;
    }

    /**
     * 如果处理器为空，就是用线程组作为处理器，因为线程组实现了这个接口
     */
    public UncaughtExceptionHandler getUncaughtExceptionHandler() {
        return uncaughtExceptionHandler != null ?
            uncaughtExceptionHandler : group;
    }

    /**
     * 设置线程处理器
     */
    public void setUncaughtExceptionHandler(UncaughtExceptionHandler eh) {
        checkAccess();
        uncaughtExceptionHandler = eh;
    }

    /**
     * 将异常转发给处理器，这个方法只会被JVM调用
     */
    private void dispatchUncaughtException(Throwable e) {
        getUncaughtExceptionHandler().uncaughtException(this, e);
    }

    /**
     * 清理引用队列，关于引用，单开帖子去讲
     */
    static void processQueue(ReferenceQueue<Class<?>> queue,
                             ConcurrentMap<? extends
                             WeakReference<Class<?>>, ?> map)
    {
        Reference<? extends Class<?>> ref;
        while((ref = queue.poll()) != null) {
            map.remove(ref);
        }
    }

    /**
     *  关于引用，单开帖子去讲
     **/
    static class WeakClassKey extends WeakReference<Class<?>> {
        /**
         * saved value of the referent's identity hash code, to maintain
         * a consistent hash code after the referent has been cleared
         */
        private final int hash;

        /**
         * Create a new WeakClassKey to the given object, registered
         * with a queue.
         */
        WeakClassKey(Class<?> cl, ReferenceQueue<Class<?>> refQueue) {
            super(cl, refQueue);
            hash = System.identityHashCode(cl);
        }

        /**
         * Returns the identity hash code of the original referent.
         */
        @Override
        public int hashCode() {
            return hash;
        }

        /**
         * Returns true if the given object is this identical
         * WeakClassKey instance, or, if this object's referent has not
         * been cleared, if the given object is another WeakClassKey
         * instance with the identical non-null referent as this one.
         */
        @Override
        public boolean equals(Object obj) {
            if (obj == this)
                return true;

            if (obj instanceof WeakClassKey) {
                Object referent = get();
                return (referent != null) &&
                       (referent == ((WeakClassKey) obj).get());
            } else {
                return false;
            }
        }
    }


    // The following three initially uninitialized fields are exclusively
    // managed by class java.util.concurrent.ThreadLocalRandom. These
    // fields are used to build the high-performance PRNGs in the
    // concurrent code, and we can not risk accidental false sharing.
    // Hence, the fields are isolated with @Contended.

    /** The current seed for a ThreadLocalRandom */
    @sun.misc.Contended("tlr")
    long threadLocalRandomSeed;

    /** Probe hash value; nonzero if threadLocalRandomSeed initialized */
    @sun.misc.Contended("tlr")
    int threadLocalRandomProbe;

    /** Secondary seed isolated from public ThreadLocalRandom sequence */
    @sun.misc.Contended("tlr")
    int threadLocalRandomSecondarySeed;

    //以下全是原生方法
    private native void setPriority0(int newPriority);
    private native void stop0(Object o);
    private native void suspend0();
    private native void resume0();
    private native void interrupt0();
    private native void setNativeName(String name);
}

```

>通读一遍Thread类的源码，其实不是很难理解，因为很多核心的操作都是使用native方法去实现的，但还是请牢记这些方法，都是基础中的基础！