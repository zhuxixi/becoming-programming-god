package org.zhuzhenxi.test.hangdian.h1089to2012.h1229;

import java.util.Scanner;

/**
 * @Author:Zhuxixi
 * @Description:
 * @Date:Create in  2018/12/23 8:35 PM
 */
public class Main {
    public static void main(String[] args){
        Scanner in = new Scanner(System.in);
        while (in.hasNext()){
            int a = in.nextInt();
            int b = in.nextInt();
            int k = in.nextInt();
            if (a==0&&b==0){
                continue;
            }

            int bits = 1;
            for (int i = 0; i < k; i++) {
                bits *=10;
            }
            if (a%bits==b%bits){
                System.out.println(-1);
                continue;
            }

            System.out.println(a+b);


        }
    }
}
