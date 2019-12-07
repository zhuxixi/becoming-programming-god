package org.zhuzhenxi.test.algs4.algs11;

public class Algs1114 {
    public static void main(String[] args){
        System.out.println(lg(2047));
    }

    public static int lg(int a){
        if (a==1){
            return 0;
        }
        return 1+lg(a/2);
    }
}
