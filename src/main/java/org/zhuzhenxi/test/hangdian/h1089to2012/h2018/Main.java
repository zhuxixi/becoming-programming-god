package org.zhuzhenxi.test.hangdian.h1089to2012.h2018;

import java.util.Scanner;

public class Main {
    private static Scanner scanner;

    public static void main(String[] args) {
        scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            int n = scanner.nextInt();
            if (n == 0)
                break;
            int []sum = new int[54];
            sum[1] = 1;
            sum[2] = 2;
            sum[3] = 3;
            sum[4] = 4;

            for (int i = 5; i <= n; i++) {
                sum[i] = sum[i-1]+sum[i-3];
            }
            System.out.println(sum[n]);
        }
    }
}