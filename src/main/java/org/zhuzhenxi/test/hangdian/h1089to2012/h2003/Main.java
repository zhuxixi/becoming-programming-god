package org.zhuzhenxi.test.hangdian.h1089to2012.h2003;

import java.io.BufferedInputStream;
import java.util.Scanner;

/**
 * @Author:Zhuxixi
 * @Description:
 * @Date:Create in  2018/12/11 10:52 PM
 */
public class Main {
    public static void main(String[] args){
        Scanner in = new Scanner(new BufferedInputStream(System.in));
        while (in.hasNext()){
            double a = in.nextDouble();
            a = Math.abs(a);
            System.out.printf("%.2f",a);
            System.out.println();
        }
    }
}
