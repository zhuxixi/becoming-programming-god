package org.zhuzhenxi.test.algs4.algs11;

/**
 * @author zhuzhenxi
 * @date 2019.04.08
 */
public class Algs1106 {

    /**
     * 打印出来的竟然是斐波那契数列
     * @param args
     */
    public static void main(String[] args){
        int f = 0;
        int g = 1;
        for (int i = 0; i <= 15; i++)
        {
            System.out.println(f);
            f = f + g;
            g = f - g;
        }
    }
}
