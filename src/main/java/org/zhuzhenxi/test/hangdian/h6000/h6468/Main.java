package org.zhuzhenxi.test.hangdian.h6000.h6468;

import java.util.*;

public class Main {
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        while (sc.hasNext()){
            int nums = sc.nextInt();
            List<String> integers = new ArrayList<>();
            while (nums>0&&sc.hasNext()){
                int a = sc.nextInt();
                integers.add(String.valueOf(a));
                nums--;
            }
            Collections.sort(integers);
//            System.out.println(integers.get());
        }

    }
}
