package org.zhuzhenxi.becoming.source.jdk.concurrency.thread.uncaughtexceptionhandler.thread;

import org.zhuzhenxi.becoming.source.jdk.concurrency.thread.uncaughtexceptionhandler.dao.LoginLoggingDAO;
import org.zhuzhenxi.becoming.source.jdk.concurrency.thread.uncaughtexceptionhandler.exception.DatasourceBusyException;
import org.zhuzhenxi.becoming.source.jdk.concurrency.thread.uncaughtexceptionhandler.service.AsynLogger;
import org.zhuzhenxi.becoming.source.jdk.concurrency.thread.uncaughtexceptionhandler.service.LoginLogBeanFactory;

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
