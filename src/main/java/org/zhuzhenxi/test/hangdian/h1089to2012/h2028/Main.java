package org.zhuzhenxi.test.hangdian.h1089to2012.h2028;

import java.util.Scanner;

public class Main {
    public static void main(String[] args){
        Scanner in = new Scanner(System.in);
        while (in.hasNext()){
            int nums = in.nextInt();
            int current = in.nextInt();
            nums = nums-1;
            while (nums>0&&in.hasNext()){
                int next = in.nextInt();
                current = lcm(current,next);
                nums--;
            }
            System.out.println(current);
        }
    }

    /**
     * 最大公约数
     * @param a
     * @param b
     * @return
     */
    private static int gcd(int a,int b){
        if (a<b){
            int temp = 0;
            temp = a;
            a = b;
            b = temp;
        }
        return a%b==0?b:gcd(b,a%b);

    }

    /**
     * 最小公倍数
     * @param a
     * @param b
     * @return
     */
    private static int lcm(int a, int b){
        //先算除法再乘，否则会超int最大值
        return (a/gcd(a,b))*b;
    }
}
