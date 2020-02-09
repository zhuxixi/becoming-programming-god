package org.zhuzhenxi.becoming.source.jdk.util.linkedhashmap;

import java.util.LinkedHashMap;
import java.util.Map;

public class LinkedHashMapTest {
    public static void main(String[] args){
        testReplace();
    }

    public static void testPutOrder(){
        LinkedHashMap<Integer,Integer> a = new LinkedHashMap<>();
        a.put(1,1);
        a.put(2,2);
        a.put(3,3);
        a.put(4,4);
        a.put(1,2);
        for (Map.Entry<Integer,Integer> entry:a.entrySet()){
            System.out.println(entry.getKey()+","+entry.getValue());
        }
    }

    public static void testReplace(){
        LinkedHashMap<Integer,Integer> a = new LinkedHashMap<>(10,0.75f,true);
        a.put(1,1);
        a.put(2,2);
        a.put(3,3);
        a.put(4,4);
        a.get(2);
        a.replace(1,1,1);
        a.get(2);
        for (Map.Entry<Integer,Integer> entry:a.entrySet()){
            System.out.println(entry.getKey()+","+entry.getValue());
        }
    }
}
