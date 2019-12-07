package org.zhuzhenxi.becoming.source.jdk.concurrency.thread.uncaughtexceptionhandler.thread;

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
