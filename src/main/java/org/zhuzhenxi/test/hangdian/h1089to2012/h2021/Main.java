package org.zhuzhenxi.test.hangdian.h1089to2012.h2021;

import java.util.Scanner;

public class Main {
    public static void main(String[] args){
        Scanner in = new Scanner(System.in);
        while (in.hasNext()){
            int a = in.nextInt();
            if (a==0||a>100){
                continue;
            }
            int result = 0;
            while (a>0&&in.hasNext()){
                int money = in.nextInt();
                result+= sum(money);
                a--;
            }
            System.out.println(result);
        }
    }

    private static int sum(int money){
        int n100 = money/100;
        int n50 = money%100/50;
        int n10 = money%100%50/10;
        int n5 = money%100%50%10/5;
        int n2 = money%100%50%10%5/2;
        int n1 = money%100%50%10%5%2;
        return n100+n50+n10+n5+n2+n1;

    }
}
