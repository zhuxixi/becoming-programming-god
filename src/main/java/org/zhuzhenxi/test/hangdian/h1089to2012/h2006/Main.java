package org.zhuzhenxi.test.hangdian.h1089to2012.h2006;

import java.util.Scanner;

/**
 * @Author:Zhuxixi
 * @Description:
 * @Date:Create in  2018/12/12 12:58 AM
 */
public class Main {
    public static void main(String[] args){
        Scanner in = new Scanner(System.in);
        while (in.hasNext()){
            int num = in.nextInt();
            int result = 1;
            for (int i = 0; i < num; i++) {
                int a = in.nextInt();
                if (a%2!=0){
                    result*=a;
                }
            }
            System.out.println(result);
        }
    }
}
