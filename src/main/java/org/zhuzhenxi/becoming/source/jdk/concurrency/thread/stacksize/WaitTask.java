package org.zhuzhenxi.becoming.source.jdk.concurrency.thread.stacksize;

/**
 * 等待任务
 * @author zhuzh
 * @date 2019.10.20
 */
public class WaitTask implements Runnable {
    private final Object locker = new Object();
    @Override
    public void run() {
        synchronized (locker){
            try {
                locker.wait();
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }
    }
}
