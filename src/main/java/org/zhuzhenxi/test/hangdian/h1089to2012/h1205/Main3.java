package org.zhuzhenxi.test.hangdian.h1089to2012.h1205;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Main3 {
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        while (sc.hasNext()){
            int testNums = sc.nextInt();
            while (testNums>0&&sc.hasNext()){
                int kinds = sc.nextInt();
                List<Integer> a = new ArrayList<>(kinds);
                for (int i = 0; i < kinds; i++) {
                    int current = sc.nextInt();
                    a.add(current);
                }
                Collections.sort(a);
                int dept = -1;
                for (int i = a.size()-1; i >=0; i--) {
                    int current = a.get(i);
                    if (dept<=1){
                        dept = current;
                        continue;
                    }
                    dept = dept-current;
                }
                System.out.println(dept);

                System.out.println(dept<=a.size()?"Yes":"No");
                testNums--;
            }
        }
    }
}
