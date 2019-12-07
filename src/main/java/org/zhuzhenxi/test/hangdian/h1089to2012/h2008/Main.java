package org.zhuzhenxi.test.hangdian.h1089to2012.h2008;

import java.util.Scanner;

/**
 * @Author:Zhuxixi
 * @Description:
 * @Date:Create in  2018/12/12 1:20 AM
 */
public class Main {
    public static void main(String[] args){
        Scanner in = new Scanner(System.in);
        while (in.hasNext()){
            double nums = in.nextDouble();
            if (nums>100||nums==0){
                continue;
            }
            int minor0 = 0;
            int zero = 0;
            int op = 0;
            for (int i = 0; i < nums; i++) {
                double c = in.nextDouble();
                if (c<0){
                    minor0++;
                    continue;
                }
                if (c==0){
                    zero++;
                continue;
                }
                op++;
            }
            System.out.println(minor0+" "+zero+" "+op);
        }
    }
}
