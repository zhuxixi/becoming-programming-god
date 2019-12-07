package org.zhuzhenxi.becoming.source.jdk.concurrency.thread.clone;

public class ThreadCloneExample {
    public static void main(String[] args){
        CloneableThread a = new CloneableThread();
        try {
            Object b = a.clone();
        }catch (CloneNotSupportedException e){
            e.printStackTrace();
        }
    }
}
