package org.zhuzhenxi.test.hangdian.h1089to2012.h2019;

import java.util.Scanner;

/**
 * @Author:Zhuxixi
 * @Description:
 * @Date:Create in  2018/12/13 11:33 PM
 */
public class Main {
    public static void main(String[] args){
        Scanner in = new Scanner(System.in);
        while (in.hasNext()){
            int n = in.nextInt();
            int m = in.nextInt();
            if (n==0&&m==0){
                continue;
            }
            int[] sort = new int[n+1];
            int i1 = 0;
            while (n>0&&in.hasNext()){


                int a = in.nextInt();

                if (m==Integer.MIN_VALUE){
                    sort[i1] =a;
                    continue;
                }
                if (m<a){
                    sort[i1] =m ;
                    sort[i1+1] = a;
                    m = Integer.MIN_VALUE;
                }
                i1++;
                n--;
            }

            for (int i = 0; i < n+1; i++) {
                System.out.print(sort[i]+" ");
            }



            System.out.println();
        }
    }
}
