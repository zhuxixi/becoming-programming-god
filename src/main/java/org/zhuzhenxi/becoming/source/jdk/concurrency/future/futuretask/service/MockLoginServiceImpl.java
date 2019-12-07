package org.zhuzhenxi.becoming.source.jdk.concurrency.future.futuretask.service;


import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MockLoginServiceImpl implements LoginService {


    private static final Map<String, Integer> SERVER_INVOKE_COUNT = new ConcurrentHashMap<>(28);




    @Override
    public int queryInvokeCount(String url) {
        return 0;
    }


}
