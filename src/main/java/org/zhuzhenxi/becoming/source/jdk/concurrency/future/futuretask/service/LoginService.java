package org.zhuzhenxi.becoming.source.jdk.concurrency.future.futuretask.service;

public interface LoginService {

    /**
     * 调用http接口查询该节点记录的请求次数
     * @param url
     * @return
     */
    int queryInvokeCount(String url);
}
