package org.zhuzhenxi.test.hangdian.h1089to2012.h1091;

import java.io.BufferedInputStream;
import java.util.Scanner;

/**
 * @Author:Zhuxixi
 * @Description:
 * @Date:Create in  2018/12/11 10:00 PM
 */
public class Main {
    public static void main(String[] args){
        Scanner in = new Scanner(new BufferedInputStream(System.in));
        while (in.hasNext()){
            int a = in.nextInt();
            int b = in.nextInt();
            if (a==0&&b==0){
                continue;
            }
            System.out.println(a+b);
        }
    }
}
