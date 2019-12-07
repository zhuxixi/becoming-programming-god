package org.zhuzhenxi.test.hangdian.h1089to2012.h2009;

import java.util.Scanner;

/**
 * @Author:Zhuxixi
 * @Description:
 * @Date:Create in  2018/12/12 1:27 AM
 */
public class Main {
    public static void main(String[] args){
        Scanner in = new Scanner(System.in);
        while (in.hasNext()){

            int finalvalue = in.nextInt();
            int m = in.nextInt();

            double[] siri = new double[m];
            double result = 0;
            for (int i = 0; i < siri.length; i++) {
                if (i==0){
                    siri[i]=finalvalue;
                    result+=siri[i];
                    continue;
                }
                siri[i] = Math.sqrt(siri[i-1]);
                result+=siri[i];
            }
            System.out.printf("%.2f",result);
            System.out.println();
        }
    }
}
