package org.zhuzhenxi.test.hangdian.h2098;

import java.util.Scanner;

/**
 * @Author:Zhuxixi
 * @Description:
 * @Date:Create in  2018/12/23 9:30 PM
 */
public class Main {
    public static void main(String[] args){
        Scanner in = new Scanner(System.in);
        while (in.hasNext()){
            int a = in.nextInt();
            int primeNums = primeNums(a);
            int result = factorial(primeNums)/(factorial(primeNums-2)*2);
            System.out.println(result);

        }
    }

    public static int primeNums(int a){
        int result = 0;
        for (int i = 3; i <= a; i=i+2) {
            if (isPrime(i)){
                result+=1;
            }
        }
        return result;
    }

    public static boolean isPrime(int a){
        for (int i = 3; i <= a; i+=2) {
            if (a%i==0){
                return false;
            }
        }
        return true;
    }

    public static int factorial(int a){
        int result = 1;
        for (int i = 1; i <=a ; i++) {
            result *= i;
        }
        return result;
    }

}
