package org.zhuzhenxi.test.hangdian.h1089to2012.h2004;

import java.io.BufferedInputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * @Author:Zhuxixi
 * @Description:
 * @Date:Create in  2018/12/11 10:55 PM
 */
public class Main {
    private static final Map<Integer,String> RANK = new HashMap<>();
    private static final String A ="A";
    private static final String B ="B";
    private static final String C ="C";
    private static final String D ="D";
    private static final String E ="E";

    static {
        for (int i = 0; i < 101; i++) {
            if (i>=90){
                RANK.put(i,A);
                continue;
            }
            if (i>=80){
                RANK.put(i,B);
                continue;
            }
            if (i>=70){
                RANK.put(i,C);
                continue;
            }
            if (i>=60){
                RANK.put(i,D);
                continue;
            }
            RANK.put(i,E);
        }


    }

    public static void main(String[] args){
        Scanner in = new Scanner(new BufferedInputStream(System.in));
        while (in.hasNext()){
            int input = in.nextInt();
            String rank = RANK.get(input);
            if (rank==null){
                System.out.println("Score is error!");
                continue;
            }
            System.out.println(rank);
        }
    }

}
