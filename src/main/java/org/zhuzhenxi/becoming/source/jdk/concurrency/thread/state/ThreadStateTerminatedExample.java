package org.zhuzhenxi.becoming.source.jdk.concurrency.thread.state;

/**
 *
 */
public class ThreadStateTerminatedExample {

    public static void main(String[] args) {

        Runnable waiting = () -> {
            System.out.println("随便执行一下，然后线程就会变为Terminated");
        };
        Thread terminate = new Thread(waiting);
        terminate.start();
        //主线程先睡1秒，让terminate先执行，一秒钟足够terminate执行完毕，然后线程就结束了
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("terminate当前的线程状态=" + terminate.getState().toString());

        System.exit(1);
    }

}
