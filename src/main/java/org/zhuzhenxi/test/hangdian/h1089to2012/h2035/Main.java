package org.zhuzhenxi.test.hangdian.h1089to2012.h2035;

import java.util.Scanner;

public class Main {
    public static void main(String[] args){
        Scanner in = new Scanner(System.in);
        while (in.hasNext()){
            int a = in.nextInt();
            int b = in.nextInt();

            if (a==0&&b==0){
                continue;
            }
            if (a<1||b>10000){
                continue;
            }

            a = a%1000;
            int tmp = 1;
            for (int i = 0; i < b; i++) {
                tmp = tmp*a;
                tmp = tmp%1000;
            }
            System.out.println(tmp);

        }
    }
}
