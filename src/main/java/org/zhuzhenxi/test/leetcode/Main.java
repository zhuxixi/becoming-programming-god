package org.zhuzhenxi.test.leetcode;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {
    public static  boolean startZero = true;
    public static AtomicInteger integer = new AtomicInteger();
    public static CountDownLatch latch = new CountDownLatch(2);
    public static void main(String[] args){
        Thread zero = new Thread(()->{
            while(integer.get()<100){
                if (startZero){
                    int cu = integer.get();
                    integer.incrementAndGet();
                    System.out.println("current even number is "+cu);
                    startZero = false;
                }
            }
            latch.countDown();
        });

        Thread one = new Thread(()->{
            while(integer.get()<100){
                if (!startZero){
                    int cu = integer.get();
                    integer.incrementAndGet();
                    System.out.println("odd number is "+cu);
                    startZero = true;
                }
            }
            latch.countDown();
        });
        zero.start();
        one.start();
        try {
            latch.await(30, TimeUnit.SECONDS);
        }catch (InterruptedException e){
            e.printStackTrace();
        }


    }
}
