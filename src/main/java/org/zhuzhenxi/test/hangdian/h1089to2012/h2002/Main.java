package org.zhuzhenxi.test.hangdian.h1089to2012.h2002;

import java.io.BufferedInputStream;
import java.util.Scanner;

/**
 * @Author:Zhuxixi
 * @Description:
 * @Date:Create in  2018/12/11 11:16 PM
 */
public class Main {
    private static final double PI = 3.1415927;
    public static void main(String[] args){
        Scanner in = new Scanner(new BufferedInputStream(System.in));
        while (in.hasNext()){
            double a = in.nextDouble();
            double result = PI*Math.pow(a,3)*4/3;
            System.out.printf("%.3f",result);
            System.out.println();
        }
    }
}
