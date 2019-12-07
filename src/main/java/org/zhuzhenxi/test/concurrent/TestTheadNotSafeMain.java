package org.zhuzhenxi.test.concurrent;

import org.zhuzhenxi.test.concurrent.thread.ImThread;
import org.zhuzhenxi.test.concurrent.thread.MyThread;

import java.util.ArrayList;
import java.util.List;

public class TestTheadNotSafeMain {

    public static List<Integer> EXAMPLE = new ArrayList<>();
    static {
        for (int i = 0; i < 10; i++) {
            EXAMPLE.add(i);
        }
    }

    public static void main(String[] args){
        MyThread thread = new MyThread(EXAMPLE);
        Thread imThread = new Thread(new ImThread(EXAMPLE));
        thread.start();
        imThread.start();
    }
}
