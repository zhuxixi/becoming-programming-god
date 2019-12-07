package org.zhuzhenxi.test.hangdian.h1089to2012.h1012;

import java.text.DecimalFormat;

/**
 * @Author:Zhuxixi
 * @Description:
 * @Date:Create in  2018/12/22 10:25 PM
 */
public class Main {
    public static void main(String[] args){
        DecimalFormat df = new DecimalFormat("0.000000000");
        System.out.println("n e");
        System.out.println("- -----------");

        for (int i = 0; i < 10; i++) {
            if (i==0){
                System.out.println(i+" "+1);
                continue;
            }
            if (i==1){
                System.out.println(i+" "+2);
                continue;
            }
            if (i==2){
                System.out.println(i+" "+2.5);
                continue;
            }
            System.out.println(i+" "+df.format(calculateE(i)));
        }

    }

    public static double calculateE(int a){

        double b = 0;
        //0 ~ a 都要参与运算, 如果是 i<a的话，i=a不会参与运算
        for (int i = 0; i < a+1; i++) {
            b += 1/factorial(i);
        }
        return b;
    }

    public static double factorial(int a){
        if (a==0||a==1){
            return 1;
        }
        return factorial(a-1)*a;
    }
}
