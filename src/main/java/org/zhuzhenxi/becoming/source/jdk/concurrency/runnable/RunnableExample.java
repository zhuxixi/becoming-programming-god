package org.zhuzhenxi.becoming.source.jdk.concurrency.runnable;


/**
 * @author zhuzh
 * @date 2019.10.12
 */
public class RunnableExample {

    public static void main(String[] args){
        Runnable runner = () -> System.out.println(Thread.currentThread().getName());
        //直接调用run方法，不会启动新的线程
        runner.run();
        Thread thread = new Thread(runner);
        //直接调用thread.run也不会启动新的线程
        thread.run();
        //只有调用start才会启动新的线程
        thread.start();
    }
}
