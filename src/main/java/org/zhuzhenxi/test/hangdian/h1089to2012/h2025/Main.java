package org.zhuzhenxi.test.hangdian.h1089to2012.h2025;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        while (in.hasNext()) {
            String a = in.next();
            char[] array = a.toCharArray();
            List<Integer> maxCommon = new ArrayList<>();
            char max = '0';
            int index = 0;
            for (int i = 0; i < array.length; i++) {
                if (array[i] >= max) {
                    max = array[i];
                    index = i;
                    maxCommon.add(i);
                }
            }
            for (Iterator<Integer> it = maxCommon.listIterator();it.hasNext();){
                int c = it.next();
                if (array[c]!=array[index]){
                    it.remove();
                }
            }

            int j = 0;
            while (j < array.length) {
                System.out.print(array[j]);
                if (maxCommon.contains(j)) {
                    System.out.print("(max)");
                }
                j = j + 1;
            }
            System.out.println();
        }
    }
}
