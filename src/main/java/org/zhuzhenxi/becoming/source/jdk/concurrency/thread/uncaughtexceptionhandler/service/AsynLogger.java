package org.zhuzhenxi.becoming.source.jdk.concurrency.thread.uncaughtexceptionhandler.service;

import org.zhuzhenxi.becoming.source.jdk.concurrency.thread.uncaughtexceptionhandler.po.LoginLogPO;
import org.zhuzhenxi.becoming.source.jdk.concurrency.thread.uncaughtexceptionhandler.thread.LoginLoggingTask;
import org.zhuzhenxi.becoming.source.jdk.concurrency.thread.uncaughtexceptionhandler.thread.LoginLoggerThread;
import org.zhuzhenxi.becoming.source.jdk.concurrency.thread.uncaughtexceptionhandler.thread.LoginLoggingUncaughtExceptionHandler;

import java.util.concurrent.ArrayBlockingQueue;

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
     * 调用startLogging方法
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
