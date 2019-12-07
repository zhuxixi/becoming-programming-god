package org.zhuzhenxi.test.hangdian.h1089to2012.h1108;

import java.util.Scanner;

/**
 * @Author:Zhuxixi
 * @Description:
 * @Date:Create in  2018/12/22 12:54 PM
 */
public class Main {
    public static void main(String[] args){
        Scanner in = new Scanner(System.in);
        while (in.hasNext()){
            int a = in.nextInt();
            int b = in.nextInt();
            System.out.println(lcm(a,b));
        }
    }


    /**
     * 最大公约数
     * @param a
     * @param b
     */
    public static int gcd(int a,int b){
        if (a<b){
            int temp = a;
            a = b;
            b = temp;
        }
        return a%b==0?b:gcd(b,a%b);
    }

    /**
     * 最小公倍数
     * @param a
     * @param b
     */
    public static int lcm(int a ,int b){
        return (a/gcd(a,b)*b);
    }
}
