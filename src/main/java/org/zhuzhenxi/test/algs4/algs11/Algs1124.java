package org.zhuzhenxi.test.algs4.algs11;

public class Algs1124 {
    public static void main(String[] args) {
        System.out.println(greatestCommonDivisor(105,24));
        System.out.println(greatestCommonDivisor(1234567,1111111));

    }

    public static int greatestCommonDivisor(int p,int q){
        System.out.println("p="+p+","+"q="+q);
        int mod = p%q;
        if (mod==0){
            return q;
        }
        p = q;
        return greatestCommonDivisor(p,mod);
    }
}
