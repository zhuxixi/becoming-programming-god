package org.zhuzhenxi.becoming.source.jdk.concurrency.future.futuretask;

import org.zhuzhenxi.becoming.source.jdk.concurrency.future.futuretask.service.server.ServerListHelper;
import org.zhuzhenxi.becoming.source.jdk.concurrency.future.futuretask.service.server.task.ServerHeartBeatTask;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;


/**
 * @author zhuzhenxi
 * @date 2019.11.09
 */
public class FutureTaskExample {

    /**
     * 线程同步器
     */
    private static final CountDownLatch LATCH = new CountDownLatch(1);

    public static void main(String[] args){
        Thread serverWatcher = new Thread(new ServerHeartBeatTask(LATCH));
        serverWatcher.setDaemon(true);
        serverWatcher.start();
        try{
            LATCH.await(30, TimeUnit.SECONDS);
            ServerListHelper.printCache();
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }
}