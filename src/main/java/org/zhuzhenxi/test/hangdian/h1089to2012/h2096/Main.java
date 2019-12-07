package org.zhuzhenxi.test.hangdian.h1089to2012.h2096;

import java.util.Scanner;

/**
 * @Author:Zhuxixi
 * @Description:
 * @Date:Create in  2018/12/23 8:30 PM
 */
public class Main {
    public static void main(String[] args){
        Scanner in = new Scanner(System.in);
        while (in.hasNext()){
            int lines = in.nextInt();
            while (lines>0&&in.hasNext()){
                int a = in.nextInt();
                int b = in.nextInt();
                System.out.println(last2(last2(a)+last2(b)));
                lines--;
            }
        }
    }

    public static int last2(int a){
        return a%100;
    }
}
