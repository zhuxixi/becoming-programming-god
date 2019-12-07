package org.zhuzhenxi.test.hangdian.h1089to2012.h2016;

import java.util.Scanner;

/**
 * @Author:Zhuxixi
 * @Description:
 * @Date:Create in  2018/12/13 6:48 PM
 */
public class Main {
    public  static void main(String[] args){
        Scanner in = new Scanner(System.in);
        while (in.hasNext()){
            int nums = in.nextInt();
            if (nums==0||nums>100){
                continue;
            }
            int[] arr = new int[nums];
            int min = Integer.MAX_VALUE;
            int index = 0;
            for (int i = 0; i < nums; i++) {
                arr[i] = in.nextInt();
                if (arr[i]<min){
                    min = arr[i];
                    index = i;
                }
            }

            int temp = 0;
            temp = arr[0];
            arr[0] = arr[index];
            arr[index] = temp;

            for (int i = 0; i < nums; i++) {
                System.out.print(arr[i]);
                if (i+1==nums){
                    continue;
                }
                System.out.print(" ");
            }
            System.out.println();
        }
    }
}
