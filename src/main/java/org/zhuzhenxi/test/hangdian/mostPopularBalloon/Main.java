package org.zhuzhenxi.test.hangdian.mostPopularBalloon;

import java.io.BufferedInputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner in = new Scanner(new BufferedInputStream(System.in));
        while (in.hasNextLine()) {
            int linesNum = Integer.parseInt(in.nextLine());
            if(linesNum <= 0 || linesNum > 1000){
                continue;
            }

            Map<String, Integer> times = new HashMap<String, Integer>();
            while (linesNum > 0 && in.hasNextLine()) {
                String str = in.nextLine();
                if (times.get(str) == null) {
                    times.put(str, 1);
                    linesNum--;
                    continue;
                }
                times.put(str, times.get(str) + 1);
                linesNum--;
            }
            String maxString = "";
            int maxNum = 0;
            for (Map.Entry<String, Integer> entry : times.entrySet()) {
                if (entry.getValue() > maxNum) {
                    maxNum = entry.getValue();
                    maxString = entry.getKey();
                }
            }
            System.out.println(maxString);
            times.clear();
        }
    }
}
