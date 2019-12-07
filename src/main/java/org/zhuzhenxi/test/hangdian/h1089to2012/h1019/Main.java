package org.zhuzhenxi.test.hangdian.h1089to2012.h1019;

import java.util.Scanner;

/**
 * @Author:Zhuxixi
 * @Description:
 * @Date:Create in  2018/12/22 9:18 PM
 */
public class Main {
    public static void main(String[] args){
        Scanner in = new Scanner(System.in);
        while (in.hasNext()){
            int lines = in.nextInt();
            while (lines>0&&in.hasNext()){
                int nums = in.nextInt();
                int[] numbers = new int[nums];
                for (int i = 0; i < nums; i++) {
                    numbers[i] = in.nextInt();
                }
                int a = numbers[0];
                for (int i = 1; i < nums; i++) {
                    a = lcm(a,numbers[i]);
                }
                System.out.println(a);
                lines--;
            }

        }
    }

    /**
     * 最小公倍数
     * @param a
     * @param b
     * @return
     */
    public static int lcm(int a,int b){
        return (a/gcd(a,b))*b;
    }

    public static int gcd(int a ,int b){
        if (a<b){
            int temp =a;
            a = b;
            b= temp;
        }
        return a%b==0?b:gcd(b,a%b);
    }

}
