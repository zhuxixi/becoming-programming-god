package org.zhuzhenxi.becoming.source.jdk.concurrency.thread.stacksize;

/**
 * @author zhuzh
 * @date 2019.10.20
 */
public class StackSizeExampleThread extends Thread {

    public StackSizeExampleThread() {
        super();
        this.setDaemon(true);
    }


    public StackSizeExampleThread(ThreadGroup group, Runnable target, String name, long stackSize) {
        super(group, target, name, stackSize);
        this.setDaemon(true);
    }

    @Override
    public void run() {
        super.run();
    }
}
