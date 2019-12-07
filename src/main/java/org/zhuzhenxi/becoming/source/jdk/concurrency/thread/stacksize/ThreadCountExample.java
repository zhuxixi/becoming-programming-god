package org.zhuzhenxi.becoming.source.jdk.concurrency.thread.stacksize;

/**
 * 指定Stack size改变线程数量
 * @author zhuzh
 * @date 2019.10.20
 */
public class ThreadCountExample {
    public static void main(String[] args){
        int[] stackSizeArr = new int[args.length];
        long stackSize = 1;
        for (int i = 0; i < args.length; i++) {
            stackSizeArr[i] = Integer.parseInt(args[i]);
            stackSize *= stackSizeArr[i];
        }

        if (stackSizeArr.length==0){
            stackSize = 1000*1000*1000;
        }

        int i = 0;
        while (true){
            i++;
            System.out.println(i);
            Thread thread = new StackSizeExampleThread(null,new WaitTask(),i+"",stackSize);
            thread.start();
        }
    }
}
