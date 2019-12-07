package org.zhuzhenxi.becoming.source.jdk.concurrency.thread.state;

/**
 *
 */
public class ThreadStateRunnableExample {
    private static boolean flag = true;

    public static void main(String[] args){
        Runnable waiting = () -> {
            //让程序空转，保持线程是runnable状态
                do { }while (flag);
        };
        Thread thread = new Thread(waiting);
        thread.start();
        try {
            //主线程先睡3秒，让子线程先跑起来，然后输出线程状态
            Thread.sleep(3000);
            System.out.printf(thread.getState().toString());
            //更改标志位，让子线程结束循环
            flag = false;
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        System.exit(1);
    }
}
