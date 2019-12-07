package org.zhuzhenxi.test.hangdian.h1089to2012.h1061;

import java.util.Scanner;

/**
 * 快速幂
 * @Author:Zhuxixi
 * @Description:
 * @Date:Create in  2018/12/22 1:31 PM
 */
public class Main {


    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        while (in.hasNext()) {
            int nums = in.nextInt();
            if (nums < 1) {
                continue;
            }
            int i = 0;
            while (i < nums && in.hasNext()) {
                int c = in.nextInt();

                System.out.println(value1(c, c));
                i = i + 1;
            }
        }

    }


    /**
     * 快速幂
     *
     * @param times
     * @param value
     * @return
     */
    public static int value1(int times, int value) {
        int ans =1;
        int base = value%10;
        while (times!=0){
            if ((times&1) == 1){
                ans *= base;
                ans %= 10;
            }
            base *=base;
            base %=10;
            times >>= 1;
        }
        return ans;
    }
}