package org.zhuzhenxi.test.hangdian.h1089to2012.h2010;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args){
        Scanner in = new Scanner(System.in);
        while (in.hasNext()){
            //默认a>b，如果a<b，互换一下
            int a = in.nextInt();
            int b = in.nextInt();

            if (a<b){
                int temp = 0;
                temp = a;
                a = b;
                b = temp;
            }

            List<Integer> result = new ArrayList<>();
            for (int i = b; i < a+1; i++) {
                int c0 = i%10;
                int c00 = i%100/10;
                int c000 = i%1000/100;
                if (Math.pow(c0,3)+Math.pow(c00,3)+Math.pow(c000,3)==i){
                    result.add(i);
                }
            }
            if (result.size()==0){
                System.out.println("no");
                continue;
            }

            for (int i = 0; i < result.size(); i++) {
                System.out.print(result.get(i));
                if (i+1==result.size()){
                    System.out.println();
                    continue;
                }
                System.out.print(" ");
            }

        }
    }
}
