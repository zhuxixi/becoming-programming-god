package org.zhuzhenxi.test.hangdian.h1089to2012.h2012;

import java.util.Scanner;

public class Main {
    public static void main(String[] args){
        Scanner in = new Scanner(System.in);
        while (in.hasNext()){
            int x = in.nextInt();
            int y = in.nextInt();
            if (x>y||x<-39||y>50||x==0&&y==0){
                continue;
            }

            boolean sorry = false;

            for (int i = x; i < y+1; i++) {
                int m = i*i+i+41;
                if (!isPrime(m)){
                    System.out.println("Sorry");
                    sorry = true;
                    break;
                }
            }

            if(sorry){
                continue;
            }
            System.out.println("OK");
        }
    }

    private static boolean isPrime(int num) {
        if (num <= 3) {
            return num > 1;
        }
        // 不在6的倍数两侧的一定不是质数
        if (num % 6 != 1 && num % 6 != 5) {
            return false;
        }
        int sqrt = (int) Math.sqrt(num);
        for (int i = 5; i <= sqrt; i += 6) {
            if (num % i == 0 || num % (i + 2) == 0) {
                return false;
            }
        }
        return true;
    }
}
