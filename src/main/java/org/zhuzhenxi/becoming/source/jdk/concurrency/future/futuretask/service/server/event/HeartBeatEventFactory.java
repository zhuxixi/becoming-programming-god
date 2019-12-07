package org.zhuzhenxi.becoming.source.jdk.concurrency.future.futuretask.service.server.event;

import java.util.Random;

/**
 * 心跳事件工厂，使用随机数决定事件
 * @author zhuzhenxi
 * @date 2019.11.09
 */
public class HeartBeatEventFactory {

    private static final Random RANDOM = new Random();

    public static BaseHeartBeatEvent getEvent(int heartBeatCount){

        //掷骰子
        int seed = RANDOM.nextInt(6);

        if (seed == 3){
            return new HeartBeatRebootEvent(heartBeatCount);
        }else {
            return new HeartBeatNormalEvent(heartBeatCount);
        }

    }
}
