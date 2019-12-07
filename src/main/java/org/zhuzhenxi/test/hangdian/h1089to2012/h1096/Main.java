package org.zhuzhenxi.test.hangdian.h1089to2012.h1096;

import java.io.BufferedInputStream;
import java.util.Scanner;

/**
 * @Author:Zhuxixi
 * @Description:
 * @Date:Create in  2018/12/11 10:28 PM
 */
public class Main {
    public static void main(String[] args){
        Scanner in = new Scanner(new BufferedInputStream(System.in));
        while (in.hasNext()){
            int lines = in.nextInt();
            for (int i = 0; i < lines; i++) {
                int head = in.nextInt();
                int result = 0;
                for (int j = 0; j < head; j++) {
                    result+=in.nextInt();
                }
                System.out.println(result);
                if (i+1<lines){
                    System.out.println();
                }
            }
        }
    }
}
