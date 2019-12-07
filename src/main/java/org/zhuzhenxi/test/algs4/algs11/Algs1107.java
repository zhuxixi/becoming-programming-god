package org.zhuzhenxi.test.algs4.algs11;

public class Algs1107 {
    public static void main(String[] args){
        a();
        b();
        System.out.println(sum(999));
        c();
        System.out.println(multi(1));
    }

    private static void a(){
        double t = 9.0;
        while (Math.abs(t - 9.0/t) > .001){
            System.out.println("差值:"+(Math.abs(t - 9.0/t)));
            t = (9.0/t + t) / 2.0;
            System.out.println(t);
        }
        System.out.printf("%.5f\n", t);
    }

    /**
     * b其实是插入排序的子过程，将数组分为两部分遍历
     * 这段代码的意义就是0~999的累加
     */
    private static void b(){
        int sum = 0;
        for (int i = 1; i < 1000; i++){
            for (int j = 0; j < i; j++){
                sum++;
            }
        }

        System.out.println(sum);
    }

    private static int sum(int a){
        if (a==0){
            return 0;
        }
        return a+sum(a-1);
    }

    /**
     * i是等比数列，1~999，2的10次方是1024，所以答案是10000
     */
    private static void c(){
        int sum = 0;
        for (int i = 1; i < 1000; i *= 2){
            for (int j = 0; j < 1000; j++){
                sum++;
            }
        }

        System.out.println(sum);
    }
    private static int multi(int i){
        if (i>1000){
            return 0;
        }
        return 1000+multi(2*i);
    }
}
