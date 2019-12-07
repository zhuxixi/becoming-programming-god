package org.zhuzhenxi.becoming.source.jdk.concurrency.future.futuretask.service.server.task;

import lombok.AllArgsConstructor;

import java.util.Random;
import java.util.concurrent.CountDownLatch;

/**
 * 一个调用者，模拟生产环境请求服务，并为服务增加请求次数
 */
@AllArgsConstructor
public class ServerInvokeTask implements Runnable {
    private CountDownLatch latch;

    @Override
    public void run() {
        Random random = new Random();

        latch.countDown();

    }
}
