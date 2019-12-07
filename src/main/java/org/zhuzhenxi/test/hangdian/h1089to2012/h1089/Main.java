package org.zhuzhenxi.test.hangdian.h1089to2012.h1089;

import java.io.BufferedInputStream;
import java.util.Scanner;

/**
 * @Author:Zhuxixi
 * @Description:
 * @Date:Create in  2018/12/9 4:50 PM
 */
public class Main {

    private static Scanner in = new Scanner(new BufferedInputStream(System.in));

    public static void main(String[] args) {
        while (in.hasNextInt()){
            int a = in.nextInt();
            int b = in.nextInt();
            System.out.println(a+b);
        }
    }
}
