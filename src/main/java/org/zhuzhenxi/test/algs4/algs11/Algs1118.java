package org.zhuzhenxi.test.algs4.algs11;

public class Algs1118 {
    public static void main(String[] args){

        System.out.println(mystery(2,25));
        System.out.println(mystery2(2,25));
    }


    /**
     * mystery(2, 25) 和 mystery(3, 11) 的返回值是多少？给定正整数 a 和 b， mystery(a,b)
     * 计算的结果是什么？将代码中的 + 替换为 * 并将 return 0 改为 return 1，然后回答相同
     * 的问题。
     * @param a
     * @param b
     * @return
     */
    public static int mystery(int a, int b)
    {
        if (b == 0) {
            return 0;
        }
        if (b % 2 == 0) {
            return mystery(a+a, b/2);
        }
        return mystery(a+a, b/2) + a;
    }
    public static int mystery2(int a, int b)
    {
        if (b == 0) {
            return 1;
        }
        if (b % 2 == 0) {
            return mystery2(a*a, b/2);
        }
        return mystery2(a*a, b/2) + a;
    }
}
