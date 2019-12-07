package org.zhuzhenxi.test.hangdian.h1089to2012.h2017;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 * @Author:Zhuxixi
 * @Description:
 * @Date:Create in  2018/12/13 7:01 PM
 */
public class Main {

    public static final List<String> STATICS = Arrays.asList("1", "2", "3", "4", "5", "6", "7", "8", "9", "0");

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        while (in.hasNext()) {
            int lines = in.nextInt();
            if (lines<=0){
                continue;
            }
            while (lines > 0 && in.hasNext()) {
                String line = in.next();
                char[] chars = line.toCharArray();
                int times = 0;
                for (int j = 0; j < chars.length; j++) {
                    if (STATICS.contains(String.valueOf(chars[j]))) {
                        times++;
                    }
                }
                System.out.println(times);
                lines--;
            }
        }
    }
}
