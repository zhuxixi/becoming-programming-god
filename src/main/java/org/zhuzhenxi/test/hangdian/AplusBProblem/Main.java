package org.zhuzhenxi.test.hangdian.AplusBProblem;

import java.io.BufferedInputStream;
import java.util.Scanner;

/**
 * @Author:Zhuxixi
 * @Description:
 * @Date:Create in  2018/12/9 4:50 PM
 */
public class Main {

    private static Scanner in = new Scanner(new BufferedInputStream(System.in));

    public static void main(String[] args) {
        while (in.hasNextLine()){
            String string = in.nextLine();
            String[] input = string.split(" ");
            System.out.println(Integer.parseInt(input[0])+Integer.parseInt(input[1]));
        }
    }
}
