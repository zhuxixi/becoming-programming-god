package org.zhuzhenxi.test.hangdian.h1089to2012.h2014;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        while (in.hasNext()) {
            int nums = in.nextInt();
            if (nums < 2 || nums > 100) {
                continue;
            }
            double max = 0;
            double min = 9999;
            double sum = 0;
            for (int i = 0; i < nums; i++) {
                double a = in.nextDouble();
                if (a > max) {
                    max = a;
                }
                if (a < min) {
                    min = a;
                }
                sum += a;
            }

            double result = (sum - max - min) / (nums - 2);
            System.out.printf("%.2f", result);
            System.out.println();
        }

    }
}
