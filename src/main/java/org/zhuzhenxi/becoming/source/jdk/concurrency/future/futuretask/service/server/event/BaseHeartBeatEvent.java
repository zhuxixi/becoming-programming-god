package org.zhuzhenxi.becoming.source.jdk.concurrency.future.futuretask.service.server.event;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.zhuzhenxi.becoming.source.jdk.concurrency.future.futuretask.service.server.ServerListHelper;

/**
 * @author zhuzhenxi
 * @date 2019.11.09
 */
@AllArgsConstructor
@NoArgsConstructor
public abstract class BaseHeartBeatEvent {

    protected int heartBeatCount;


    /**
     *
     */
    public void heartbeat(){
        invoke();
        //处理心跳事件后要记录一次服务列表快照到LRU
        ServerListHelper.recordHistory();
    }
    /**
     *
     */
    public abstract void invoke();

}
