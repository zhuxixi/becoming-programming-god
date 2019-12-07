package org.zhuzhenxi.becoming.source.jdk.concurrency.future.futuretask.service.cache;

import java.util.Iterator;
import java.util.Map;

public class LruCacheExample {

    public static void main(String[] args){
        SimpleLruCache<Integer, Integer> lruCache = new SimpleLruCache<>(4);

        for (int i = 0; i < 6; i++) {
            lruCache.put(i,i);
        }

        for (Iterator it = lruCache.entrySet().iterator(); it.hasNext();){
            Map.Entry entry = (Map.Entry)it.next();
            System.out.println("key="+entry.getKey()+",value="+entry.getValue());
        }

    }
}
