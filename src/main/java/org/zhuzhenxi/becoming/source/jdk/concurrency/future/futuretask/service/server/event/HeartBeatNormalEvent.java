package org.zhuzhenxi.becoming.source.jdk.concurrency.future.futuretask.service.server.event;

/**
 *
 * @author zhuzhenxi
 * @date 2019.11.09
 */
public class HeartBeatNormalEvent extends BaseHeartBeatEvent {

    public HeartBeatNormalEvent(int heartBeatCount){
        super(heartBeatCount);
    }

    @Override
    public void invoke() {
        System.out.println(heartBeatCount+".服务列表Watcher:节点列表无变化，一切正常");
    }
}
