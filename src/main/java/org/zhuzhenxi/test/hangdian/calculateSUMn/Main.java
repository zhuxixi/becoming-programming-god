package org.zhuzhenxi.test.hangdian.calculateSUMn;

import java.io.BufferedInputStream;
import java.util.Scanner;

/**
 * @Author:Zhuxixi
 * @Description:
 * @Date:Create in  2018/12/9 5:29 PM
 */
public class Main {
    private static Scanner in = new Scanner(new BufferedInputStream(System.in));

    public static void main(String[] args) {
        while (in.hasNext()) {
            int n = in.nextInt();
            Double result = n / 2.0 * (n + 1);
            System.out.println(result.intValue());
            System.out.println();
        }
    }

}
