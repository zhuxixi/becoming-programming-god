package org.zhuzhenxi.test.hangdian.h1089to2012.h2030;

import java.util.Scanner;

public class Main {
    public static void main(String[] args){
        Scanner in = new Scanner(System.in);
        while (in.hasNext()){
            int lines = in.nextInt();
            while (lines>0&&in.hasNext()){
                String line = in.nextLine();
                if ("".equals(line)){
                    continue;
                }
                int chineseNum = 0;
                char[] chars = line.toCharArray();
                for (int i = 0; i < chars.length; i++) {
                    int num = chars[i];
                    if (num > 127) {
                        chineseNum = chineseNum+1;
                    }
                }
                System.out.println(chineseNum);
                lines--;
            }
        }
    }

}
