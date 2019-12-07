package org.zhuzhenxi.test.kaoshi.two;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        while (sc.hasNext()){
            int nums = sc.nextInt();
            List<Integer> a = new ArrayList<>();
            while (nums>0&&sc.hasNext()){
                int ab = sc.nextInt();
                a.add(ab);
                nums--;
            }
            Collections.sort(a);
            for (int i = 0; i < a.size(); i++) {
                System.out.print(a.get(i));
                if (i != (a.size()-1)){
                    System.out.print(" ");
                }
            }
            System.out.println();
        }
    }
}
