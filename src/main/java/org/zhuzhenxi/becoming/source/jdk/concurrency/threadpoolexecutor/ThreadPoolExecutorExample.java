package org.zhuzhenxi.becoming.source.jdk.concurrency.threadpoolexecutor;

/**
 * JDK线程池研究
 * @author zhuzh
 * @date 2019.10.11
 */
public class ThreadPoolExecutorExample {
//    private static ThreadPoolExecutor executor = new ThreadPoolExecutor();
    private static final int COUNT_BITS = Integer.SIZE - 3;
    private static final int RUNNING    = -1 << COUNT_BITS;
    private static final int SHUTDOWN   =  0 << COUNT_BITS;
    private static final int STOP       =  1 << COUNT_BITS;
    private static final int TIDYING    =  2 << COUNT_BITS;
    private static final int TERMINATED =  3 << COUNT_BITS;
    private static final int CAPACITY   = (1 << COUNT_BITS)-1;

    public static void main(String[] args){
        System.out.println(Integer.toBinaryString(29));
        System.out.println(Integer.toBinaryString(CAPACITY));

        System.out.println(Integer.toBinaryString(RUNNING));
        System.out.println(Integer.toBinaryString(SHUTDOWN));

        System.out.println(Integer.toBinaryString(STOP));
        System.out.println(Integer.toBinaryString(TIDYING));
        System.out.println(Integer.toBinaryString(TERMINATED));
        System.out.println(Integer.toBinaryString(-1));

    }
}
