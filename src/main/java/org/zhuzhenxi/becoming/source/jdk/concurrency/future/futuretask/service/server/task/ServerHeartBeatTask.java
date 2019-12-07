package org.zhuzhenxi.becoming.source.jdk.concurrency.future.futuretask.service.server.task;

import lombok.AllArgsConstructor;
import org.zhuzhenxi.becoming.source.jdk.concurrency.future.futuretask.service.server.ServerListHelper;

import java.util.concurrent.CountDownLatch;

/**
 *
 * @author zhuzhenxi
 * @date 2019.11.09
 */
@AllArgsConstructor
public class ServerHeartBeatTask implements Runnable {

    private CountDownLatch latch;

    @Override
    public void run() {
        ServerListHelper.heartBeat();
        latch.countDown();
    }
}
