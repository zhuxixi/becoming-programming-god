package org.zhuzhenxi.test.gc;

/**
 * @author zhuzhenxi
 * @date 2019.02.12
 */
public class ReferenceCountingGC {
    public Object instance = null;
    private static final int mb = 1024*1024;
    private byte[] bigSize = new byte[2*mb];
    public static void main(String[] args){
        ReferenceCountingGC a = new ReferenceCountingGC();
        ReferenceCountingGC b = new ReferenceCountingGC();
        a.instance = b;
        b.instance = a;
        a = null;
        b = null;
        System.gc();

    }
}
