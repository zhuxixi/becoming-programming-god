package org.zhuzhenxi.becoming.source.jdk.concurrency;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class CyclicBarrierExample {
    private static CyclicBarrier b = new CyclicBarrier(2,()-> System.out.println("开搞了"));

    public static void main(String[] args){
        Thread a = new Thread(()->{
            try {
                b.await();
            }catch (BrokenBarrierException | InterruptedException e){
                e.printStackTrace();
            }
        });
        Thread bb = new Thread(()->{
            try {
                b.await();
            }catch (BrokenBarrierException | InterruptedException e){
                e.printStackTrace();
            }
        });
        a.start();
        bb.start();

    }
}
