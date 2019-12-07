package org.zhuzhenxi.test.hangdian.h1089to2012.h1095;

import java.io.BufferedInputStream;
import java.util.Scanner;

/**
 * @Author:Zhuxixi
 * @Description:
 * @Date:Create in  2018/12/11 10:26 PM
 */
public class Main {
    public static void main(String[] args){
        Scanner scanner = new Scanner(new BufferedInputStream(System.in));
        while (scanner.hasNext()){
            int a = scanner.nextInt();
            int b = scanner.nextInt();
            System.out.println(a+b);
            System.out.println();
        }
    }
}
