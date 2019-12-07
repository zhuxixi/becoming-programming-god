package org.zhuzhenxi.test.hangdian.h1089to2012.h2011;

import java.util.Scanner;

public class Main {
    public static void main(String[] args){
        Scanner in = new Scanner(System.in);
        while (in.hasNext()){
            int lines = in.nextInt();
            if (lines>100){
                continue;
            }
            for (int i = 0; i < lines; i++) {
                int a = in.nextInt();
                int tag = -1;
                double result = 0;
                for (int j = 1; j < a+1; j++) {
                    tag *= -1;
                    double c = j;
                    result= result+(1/c)*tag;
                }
                System.out.printf("%.2f",result);
                System.out.println();
                
            }
        }
    }
}
