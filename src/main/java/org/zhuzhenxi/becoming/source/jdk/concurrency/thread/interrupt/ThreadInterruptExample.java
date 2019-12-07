package org.zhuzhenxi.becoming.source.jdk.concurrency.thread.interrupt;

/**
 * @author zhuzh
 * @date 2019.
 */
public class ThreadInterruptExample {
    private static boolean flag = true;

    public static void main(String[] args){
        Runnable waiting = () -> {
            try{
                Thread.sleep(30000);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        };
        Thread thread = new Thread(waiting);
        thread.start();
        try {
            System.out.println("isInterrupt = "+thread.isInterrupted());
//            thread.interrupt();
            //主线程先睡3秒，让子线程先跑起来，然后输出线程状态
            Thread.sleep(3000);
            System.out.println(thread.getState().toString());

            System.out.println("isInterrupt = "+thread.isInterrupted());
            System.out.println("ClassLoader = "+thread.getContextClassLoader());

            //更改标志位，让子线程结束循环
            flag = false;
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        System.exit(1);
    }
}
