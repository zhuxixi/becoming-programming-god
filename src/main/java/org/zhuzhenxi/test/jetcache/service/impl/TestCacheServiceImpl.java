package org.zhuzhenxi.test.jetcache.service.impl;

import com.alicp.jetcache.anno.CacheRefresh;
import com.alicp.jetcache.anno.CacheType;
import com.alicp.jetcache.anno.CacheUpdate;
import com.alicp.jetcache.anno.Cached;
import org.springframework.stereotype.Service;
import org.zhuzhenxi.test.jetcache.service.TestCacheService;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class TestCacheServiceImpl implements TestCacheService {

    private static String value = "";

    private static List<String> valueList = new ArrayList<>();

    /**
     * 单参数
     * 缓存过期时间 3min
     * 自动刷新时间间隔 2min
     * Key最后一次读取后2min停止刷新
     * 个人认为：缓存刷新时间间隔=停止刷新时间<缓存过期时间
     * @param param
     * @return
     */
    @Cached(name="testCache", key="'queryValue' + ':' + #param",expire = 3,timeUnit = TimeUnit.MINUTES,cacheType = CacheType.LOCAL,area = "default")
    @CacheRefresh(refresh = 2,stopRefreshAfterLastAccess=2,timeUnit = TimeUnit.MINUTES)
    @Override
    public String queryValue(String param) {
        System.out.println("进入了queryValue方法体");
        System.out.println("当前数据是"+value);
        return value;
    }
    /**
     * 单参数
     * 过期时间
     * @param param
     * @return
     */
//    @CacheUpdate(name="testCache", key="#root.targetClass.simpleName + ':' + #param",value = "#result",area = "default")
    @Override
    public String putValue(String param) {
        System.out.println("进入了putValue方法体");
        value = param+"321321321";
        return value;
    }
    /**
     * 双参数组合Key
     * @param param1
     * @param param2
     * @return
     */
    @Cached(name="testCache", key="#param1+#param2",expire = 1,timeUnit = TimeUnit.MINUTES,cacheType = CacheType.LOCAL,area = "default")
    @Override
    public List<String> queryMultipleValue(String param1, String param2) {
        System.out.println("进入了queryMultipleValue方法体");
        return valueList;
    }

    /**
     * 双参数组合Key
     * @param param1
     * @param param2
     * @return
     */
    @CacheUpdate(name="testCache", key="#param1+#param2",value = "#result",area = "default")
    @Override
    public List<String> putMultipleValue(String param1, String param2) {
        System.out.println("进入了putMultipleValue方法体");
        valueList.add(param1+param2);
        return valueList;
    }
}
