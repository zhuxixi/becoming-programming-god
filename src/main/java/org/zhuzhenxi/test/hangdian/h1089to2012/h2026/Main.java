package org.zhuzhenxi.test.hangdian.h1089to2012.h2026;

import java.util.Scanner;

public class Main {

    public static void main(String[] args){
        Scanner in = new Scanner(System.in);
        while (in.hasNext()){
            String word = in.nextLine();
            if (word.length()>100){
                continue;
            }
            String[] words = word.split(" ");
            for (int i = 0; i < words.length; i++) {
                char[] chars = words[i].toCharArray();
                chars[0] = Character.toUpperCase(chars[0]);

                for (int j = 0; j < chars.length; j++) {
                    System.out.print(chars[j]);
                }
                if (i+1==words.length){
                    continue;
                }
                System.out.print(" ");
            }
            System.out.println();


        }
    }
}
