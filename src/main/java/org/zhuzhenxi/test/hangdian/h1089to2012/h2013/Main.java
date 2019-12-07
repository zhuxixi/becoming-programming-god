package org.zhuzhenxi.test.hangdian.h1089to2012.h2013;

import java.util.Scanner;

public class Main {
    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()){
            int days = scanner.nextInt();
            System.out.println(eat(days));
        }
    }

    /**
     * n天吃m个桃子
     * @param n n天
     * @return
     */

    private static int eat(int n){
        if (n==1){
            return 1;
        }
        return 2*(eat(n-1)+1);
    }
}
