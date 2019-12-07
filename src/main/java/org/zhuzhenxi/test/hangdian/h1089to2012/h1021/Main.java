package org.zhuzhenxi.test.hangdian.h1089to2012.h1021;

import java.io.BufferedInputStream;
import java.util.Scanner;

/**
 * @Author:Zhuxixi
 * @Description:
 * @Date:Create in  2018/12/11 9:24 PM
 */
public class Main {

    private static final String YES = "yes";
    private static final String NO = "no";

    public static void main(String[] orgs){
        Scanner in = new Scanner(new BufferedInputStream(System.in));

//        while (in.hasNextInt()){
//            int input = in.nextInt();
//            if (input>1000000){
//                continue;
//            }
//            int result = fibonacci(input);
//            if (result%3==0){
//                System.out.println(YES);
//                continue;
//            }
//            System.out.println(NO);
//        }


        while (in.hasNext()) {
            int n = in.nextInt();
            if (n % 4 == 2) {
                System.out.println(YES);
            } else {
                System.out.println(NO);
            }
        }
    }

    /**
     * 斐波那契 递归
     * @param n
     * @return
     */
    private static int fibonacci(int n){
        if (n==0){
            return 7;
        }
        if (n==1){
            return 11;
        }
        return fibonacci(n-1)+fibonacci(n-2);
    }


}
