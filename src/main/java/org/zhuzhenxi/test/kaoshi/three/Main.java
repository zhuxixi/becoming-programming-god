package org.zhuzhenxi.test.kaoshi.three;

import java.util.Scanner;

public class Main {
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        while (sc.hasNext()){
            String line = sc.nextLine();
            char[] chars = line.toCharArray();
            for (int i = chars.length-1; i >=0 ; i--) {
                System.out.print(chars[i]);
            }
            System.out.println();
        }
    }
}
