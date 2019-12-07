package org.zhuzhenxi.test.algs4.algs11;

public class Algs1120 {
    public static void main(String[] args) {
        int param = 4;
        int factorialResult = factorial(param);
        System.out.println("阶乘结果="+factorialResult);
        System.out.println(lg(param));
        System.out.println(Math.log(factorialResult));
    }
    public static double lg(int a){
        if (a==1){
            return 0;
        }
        double la = Math.log(a);
        return la+lg(a-1);
    }

    private static int factorial(int n){
        if (n==1){
            return 1;
        }
        return n*factorial(n-1);
    }
}
