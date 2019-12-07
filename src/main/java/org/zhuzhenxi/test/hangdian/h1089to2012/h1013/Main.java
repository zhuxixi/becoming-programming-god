package org.zhuzhenxi.test.hangdian.h1089to2012.h1013;

import java.math.BigInteger;
        import java.util.Scanner;

/**
 * @Author:Zhuxixi
 * @Description:
 * @Date:Create in  2018/12/23 11:46 AM
 */
public class Main {
    public static void main(String[] args){
        Scanner in = new Scanner(System.in);
        while (in.hasNext()){
            BigInteger a = in.nextBigInteger();
            if (a.equals(BigInteger.ZERO)){
                continue;
            }
            System.out.println(plusDigitalRoot(a));
        }
    }

    /**
     * 递归
     * @param a
     * @return
     */
    public static int plusDigitalRoot(BigInteger a){
        if (a.compareTo(BigInteger.TEN)<0){
            return a.intValue();
        }

        String a0 = a.toString();
        char[] chars = a0.toCharArray();
        BigInteger result = BigInteger.ZERO;

        for (int i = 0; i < chars.length; i++) {
            result =  result.add(new BigInteger(String.valueOf(char2int(chars[i]))));
        }
        return plusDigitalRoot(new BigInteger(String.valueOf(result)));
    }
    /**
     * 递归
     * @param a
     * @return
     */
    public static BigInteger modDigitalRoot(BigInteger a){
        a = a.mod(new BigInteger("9"));
        return a;
    }
    private static int char2int(char input){
        return (int)input - (int)'0';
    }
}
