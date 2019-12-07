package org.zhuzhenxi.test.hangdian.h1089to2012.h1092;

import java.io.BufferedInputStream;
import java.util.Scanner;

/**
 * @Author:Zhuxixi
 * @Description:
 * @Date:Create in  2018/12/11 10:04 PM
 */
public class Main {
    public static void main(String[] args) {
        Scanner in = new Scanner(new BufferedInputStream(System.in));
        while (in.hasNext()) {
            int nums = in.nextInt();
            if (nums==0){
                continue;
            }
            int result = 0;
            while (nums>0){
                result+=in.nextInt();
                nums--;
            }
            System.out.println(result);
        }
    }
}
