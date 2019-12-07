package org.zhuzhenxi.test.hangdian.h1089to2012.h2029;

import java.util.Scanner;

public class Main {
    public static void main(String[] args){
        Scanner in = new Scanner(System.in);
        while (in.hasNext()){
            int lines = in.nextInt();
            while (lines>0&&in.hasNext()){
                String word = in.next();
                char[] chars = word.toCharArray();
                boolean isWanted = true;
                for (int i = 0; i < chars.length; i++) {
                    if (chars[i]!=chars[chars.length-1-i]){
                        isWanted = false;
                        break;
                    }
                }
                if (isWanted){
                    System.out.println("yes");
                }else {
                    System.out.println("no");
                }
                lines--;
            }
        }
    }
}
