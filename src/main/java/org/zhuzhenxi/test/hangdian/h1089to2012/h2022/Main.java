package org.zhuzhenxi.test.hangdian.h1089to2012.h2022;

import java.util.Scanner;

public class Main {
    public static void main(String[] args){
        Scanner in = new Scanner(System.in);
        while (in.hasNext()){
            //行
            int m = in.nextInt();
            //列
            int n = in.nextInt();

            int maxM = 1;
            int maxN = 1;
            int max = 0;
            int j = 1;
            while (j<m+1&&in.hasNext()){
                for (int i = 1; i < n+1; i++) {
                    int value = in.nextInt();
                    if (Math.abs(value)>Math.abs(max)){
                        max = value;
                        maxM = j;
                        maxN = i;
                    }
                }
                j = j+1;
            }
            System.out.println(maxM+" "+maxN+" "+max);
        }
    }
}
