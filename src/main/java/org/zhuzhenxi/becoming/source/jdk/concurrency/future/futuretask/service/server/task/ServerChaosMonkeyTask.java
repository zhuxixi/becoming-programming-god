package org.zhuzhenxi.becoming.source.jdk.concurrency.future.futuretask.service.server.task;

import lombok.AllArgsConstructor;

import java.util.concurrent.CountDownLatch;

/**
 * 一个Monkey，随机移除一个节点，模拟生产环境节点挂掉的情况，
 * @author zhuzhenxi
 * @date 2019.11.09
 */
@AllArgsConstructor
public class ServerChaosMonkeyTask implements Runnable {
    private CountDownLatch latch;

    @Override
    public void run() {

        latch.countDown();
    }
}
