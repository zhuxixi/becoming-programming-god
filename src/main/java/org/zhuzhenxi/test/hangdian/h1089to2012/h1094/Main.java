package org.zhuzhenxi.test.hangdian.h1089to2012.h1094;

import java.io.BufferedInputStream;
import java.util.Scanner;

/**
 * @Author:Zhuxixi
 * @Description:
 * @Date:Create in  2018/12/11 10:24 PM
 */
public class Main {
    public static void main(String[] args){
        Scanner in = new Scanner(new BufferedInputStream(System.in));

        while (in.hasNext()){
            int nums = in.nextInt();
            int result = 0;
            for (int i = 0; i < nums; i++) {
                result+=in.nextInt();
            }
            System.out.println(result);
        }
    }
}
