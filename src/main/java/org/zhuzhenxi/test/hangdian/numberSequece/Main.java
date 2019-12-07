package org.zhuzhenxi.test.hangdian.numberSequece;

import java.io.BufferedInputStream;
import java.util.Scanner;

public class Main {
    private static int a = 0;
    private static int b = 0;
    public static void main(String[] args){
        Scanner in = new Scanner(new BufferedInputStream(System.in));
        while (in.hasNext()){
            String str = in.nextLine();
            String[] strings = str.split(" ");
             a = Integer.parseInt(strings[0]);
             b = Integer.parseInt(strings[1]);
            int n = Integer.parseInt(strings[2]);
            if (a<1||b>1000||n<1||n>100000000){
                continue;
            }
            System.out.println(numberSequence(n%48));
            a = 0;
            b = 0;
        }
    }


    private static int numberSequence(int n){
        if (n==1){
            return 1;
        }
        if (n==2){
            return 1;
        }
        return (a*numberSequence(n-1)+b*numberSequence(n-2))%7;
    }
}
