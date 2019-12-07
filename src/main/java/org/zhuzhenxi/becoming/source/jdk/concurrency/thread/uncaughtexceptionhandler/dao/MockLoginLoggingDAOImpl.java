package org.zhuzhenxi.becoming.source.jdk.concurrency.thread.uncaughtexceptionhandler.dao;

import org.zhuzhenxi.becoming.source.jdk.concurrency.thread.uncaughtexceptionhandler.exception.DatasourceBusyException;
import org.zhuzhenxi.becoming.source.jdk.concurrency.thread.uncaughtexceptionhandler.po.LoginLogPO;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

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
