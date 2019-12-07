package org.zhuzhenxi.test.forkjoin;

import java.util.concurrent.atomic.AtomicInteger;

public class TestForkResultCollector {

    private static AtomicInteger result = new AtomicInteger();
    public static void print(){
        System.out.println(result.get());
    }
    public static void increment(){
        result.getAndIncrement();
    }



}
