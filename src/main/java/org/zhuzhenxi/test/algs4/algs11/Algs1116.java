package org.zhuzhenxi.test.algs4.algs11;

public class Algs1116 {
    public static void main(String[] args){
        /**
         * 6
         * 3 + 6 + 4 +6
         * "" +3 + 1 +6 + 4 +6
         * "" +3 + "" + 1 + "" +1 + 6 + 1+1+2 +  +6
         */

        System.out.println(exR1(6));
    }
    public static String exR1(int n)
    {
        if (n <= 0) {
            return "";
        }
        return exR1(n-3) + n + exR1(n-2) + n;
    }
}
