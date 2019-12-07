package org.zhuzhenxi.becoming.source.jdk.concurrency.future.futuretask.service.cache;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 一个基于LinkedHashMap实现的简单LRU缓存
 *
 * @author zhuzhenxi
 * @date 2019.11.09
 * @param <K>
 * @param <V>
 */
public class SimpleLruCache<K,V> extends LinkedHashMap<K,V> {

    //定义缓存的容量
    private int capacity;
    private static final long serialVersionUID = 1L;
    public SimpleLruCache(int capacity){
        //调用LinkedHashMap的构造器，传入以下参数
        super(16,0.75f,true);
        //传入指定的缓存最大容量
        this.capacity=capacity;
    }

    @Override
    protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
        return size()>capacity;
    }

}
