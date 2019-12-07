package org.zhuzhenxi.test.hangdian.h1089to2012.h1235;

import java.util.Scanner;

/**
 * @Author:Zhuxixi
 * @Description:
 * @Date:Create in  2018/12/23 8:42 PM
 */
public class Main {
    public static void main(String[] args){
        Scanner in = new Scanner(System.in);
        while (in.hasNext()){
            int nums = in.nextInt();
            if (nums==0){
                continue;
            }
            int[] numbers = new int[nums];
            int i = 0;
            while (i<nums&&in.hasNext()){
                numbers[i] = in.nextInt();
                i = i+1;
            }
            while (in.hasNext()){
                int expect = in.nextInt();
                int count = 0;
                for (int j = 0; j < nums; j++) {
                    if (numbers[j]==expect){
                        count+=1;
                    }
                }
                System.out.println(count);
                break;
            }


        }
    }
}
