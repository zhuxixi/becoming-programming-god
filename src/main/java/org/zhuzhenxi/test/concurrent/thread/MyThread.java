package org.zhuzhenxi.test.concurrent.thread;

import org.zhuzhenxi.test.concurrent.TestTheadNotSafeMain;

import java.util.List;

public class MyThread extends Thread {

    private List<Integer> mine = null;

    public MyThread(List<Integer> a){
        mine = a;
    }

    @Override
    public synchronized void start() {
        super.start();
    }

    @Override
    public void run() {
       System.out.println("I am the one that extends Thread");
       synchronized (TestTheadNotSafeMain.EXAMPLE){
           for (int i = 0; i < mine.size(); i++) {
               Integer aaa = mine.get(i);
               aaa+=1;
               mine.add(aaa);
           }
       }

    }

}
