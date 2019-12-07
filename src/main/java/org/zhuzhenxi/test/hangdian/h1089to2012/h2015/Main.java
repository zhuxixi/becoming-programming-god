package org.zhuzhenxi.test.hangdian.h1089to2012.h2015;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {

    public static final Map<Integer,Integer> map = new HashMap<>();

    public static void main(String[] args){
        Scanner in = new Scanner(System.in);
        while (in.hasNext()){
            int n = in.nextInt();
            int m = in.nextInt();

            if (n<m||n>100){
                continue;
            }
            int cut = n%m;
            int ms = n/m;
            for (int i = 1; i < ms+1;i++) {
                System.out.print((sumEven(i*m)-sumEven((i-1)*m))/m);
                if (i+1==ms+1&&cut==0){
                    continue;
                }
                System.out.print(" ");
            }
            if (cut!=0){
                System.out.print((sumEven(n)-sumEven(ms*m))/cut);
            }
            System.out.println();
        }
    }

    /**
     * 算出 2 开始的 n个有序递增偶数列之和
     * @param n
     * @return
     */
    private static int sumEven(int n){
        if (n==0){
            return 0;
        }
        Integer cache = map.get(n);
        if (cache == null){
            int value = 2*n+sumEven(n-1);
            map.put(n,value);
        }
        return map.get(n);
    }
}
