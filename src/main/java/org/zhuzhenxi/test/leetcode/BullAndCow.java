package org.zhuzhenxi.test.leetcode;

import java.util.List;

public class BullAndCow {
    public static void main(String[] args) {

    }

    public String getHint(String secret, String guess) {
        int a = 0;
        int b = 0;
        //记录guess里面哪些数字不对
        int[] notRight = new int[10];
        //记录那些数字对了
        int[] right = new int[10];
        for (int i = 0; i < secret.length(); i++) {
            int s1 = secret.charAt(i);
            int g1 = guess.charAt(i);
            if (s1 == g1) {
                a += 1;

                continue;
            }
            //对应的数字+1，这些数字是秘密数字，但是没有猜对
            right[s1 - '0']++;
            //对应的没猜对的数字
            notRight[g1 - '0']++;
        }
        /**
         * 为什么要取最小
         * secret=1100,guess=1111这个用例，只有第一位的1才是对的。
         * guess后三个1，其实只算1个B
         * 因为secret里只有2个1，其中第一个1，已经猜对了，它已经不算了
         * 虽然guess有3个1，但是secret只有一个1，所以我们要以right中的数字为准
         * right[1]=1,notRight[1]=3
         */
        for (int j = 0; j < 10; j++) {
            b += Math.min(right[j], notRight[j]);
        }
        return a + "A" + b + "B";
    }
}

