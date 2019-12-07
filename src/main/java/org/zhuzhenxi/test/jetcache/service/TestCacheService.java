package org.zhuzhenxi.test.jetcache.service;

import java.util.List;

/**
 * JetCache学习笔记
 * @author zhuzhenxi
 * @date 2018.06.07
 */
public interface TestCacheService {


    String queryValue(String param);


    String putValue(String param);


    List<String> queryMultipleValue(String param1, String param2);


    List<String> putMultipleValue(String param1,String param2);
}
