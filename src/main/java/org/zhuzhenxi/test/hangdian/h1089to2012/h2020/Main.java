package org.zhuzhenxi.test.hangdian.h1089to2012.h2020;

import java.util.Scanner;

public class Main {
    public static void main(String[] args){
        Scanner in = new Scanner(System.in);
        while (in.hasNext()){
            int nums = in.nextInt();
            if (nums==0){
                continue;
            }
            int[] a = new int[nums];
            int i = 0;
            while (nums>0&&in.hasNext()){
                int b = in.nextInt();
                a[i] = b;
                i++;
                nums--;
            }

            for (int j = 1; j < a.length; j++) {
                int c = a[j];
                int k =j-1;
                while (k>=0&&Math.abs(a[k])<Math.abs(c)){
                    a[k+1] = a[k];
                    k--;
                }
                a[k+1] = c;
            }
            for (int j = 0; j < a.length; j++) {
                System.out.print(a[j]);
                if (j+1==a.length){
                    continue;
                }
                System.out.print(" ");
            }
            System.out.println();
        }
    }
}
