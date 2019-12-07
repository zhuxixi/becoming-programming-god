package org.zhuzhenxi.becoming.source.jdk.concurrency.future.futuretask.service.server.event;

import org.zhuzhenxi.becoming.source.jdk.concurrency.future.futuretask.service.server.ServerListHelper;

public class HeartBeatRebootEvent extends BaseHeartBeatEvent {

    public HeartBeatRebootEvent(int heartBeatCount){
        super(heartBeatCount);
    }
    @Override
    public void invoke() {
        System.out.println(heartBeatCount+".服务列表Watcher:节点列表发生变化，可能集群重启了");
        ServerListHelper.initialMockServerList();
        //记录重启次数
        ServerListHelper.recordRebootCount();
    }
}
