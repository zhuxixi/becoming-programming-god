package org.zhuzhenxi.test.hangdian.h1089to2012.h1090;

import java.io.BufferedInputStream;
import java.util.Scanner;

/**
 * @Author:Zhuxixi
 * @Description:
 * @Date:Create in  2018/12/11 9:57 PM
 */
public class Main {
    public static void main(String[] args){
        Scanner in = new Scanner(new BufferedInputStream(System.in));
        while (in.hasNext()){
            int lines = in.nextInt();
            while (lines>0){
                int a = in.nextInt();
                int b = in.nextInt();
                System.out.println(a+b);
                lines--;
            }
        }
    }
}
