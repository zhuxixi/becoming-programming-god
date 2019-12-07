package org.zhuzhenxi.test.hangdian.h1089to2012.h2001;

import java.io.BufferedInputStream;
import java.util.Scanner;

/**
 * @Author:Zhuxixi
 * @Description:
 * @Date:Create in  2018/12/11 10:35 PM
 */
public class Main {
    public static void main(String[] args){
        Scanner in = new Scanner(new BufferedInputStream(System.in));
        while (in.hasNext()){
            double x1 = in.nextDouble();
            double y1 = in.nextDouble();
            double x2 = in.nextDouble();
            double y2 = in.nextDouble();

            double result = Math.sqrt((x1-x2)*(x1-x2)+(y1-y2)*(y1-y2));
            System.out.printf("%.2f",result);
            System.out.println();
        }
    }
}
