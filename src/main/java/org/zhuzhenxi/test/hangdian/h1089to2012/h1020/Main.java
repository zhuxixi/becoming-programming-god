package org.zhuzhenxi.test.hangdian.h1089to2012.h1020;

import java.util.*;

/**
 * @Author:Zhuxixi
 * @Description:
 * @Date:Create in  2018/12/22 9:29 PM
 */
public class Main {
    public static void main(String[] args){
        Scanner in = new Scanner(System.in);
        while (in.hasNext()){
            int lines = in.nextInt();
            if (lines>100||lines<1){
                continue;
            }
            while (lines>0&&in.hasNext()){
                String string = in.next();
                if (string.length()>10000){
                    continue;
                }
                char[] chars = string.toCharArray();
                char current = chars[0];
                int times = 1;
                for (int i = 1; i < chars.length; i++) {
                    if (chars[i]!=current){
                        if (times!=1){
                            System.out.print(times);
                        }
                        System.out.print(current);
                        current = chars[i];
                        times=1;
                        continue;
                    }
                    times +=1;
                }
                if (times!=1){
                    System.out.print(times);
                }
                System.out.print(current);
                System.out.println();
                lines--;
            }
        }
    }
}
