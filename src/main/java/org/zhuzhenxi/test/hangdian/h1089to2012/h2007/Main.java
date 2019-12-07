package org.zhuzhenxi.test.hangdian.h1089to2012.h2007;

import java.util.Scanner;

/**
 * @Author:Zhuxixi
 * @Description:
 * @Date:Create in  2018/12/12 1:07 AM
 */
public class Main {
    public static void main(String[] args){
        Scanner in = new Scanner(System.in);

        while (in.hasNext()){
            int a = in.nextInt();
            int b = in.nextInt();
            int temp = 0;

            if (a>b){
                temp=a;
                a = b;
                b = temp;
            }
            int[] nums = new int[b-a+1];
            for (int i = 0; i < nums.length; i++) {
                nums[i] = i+a;
            }
            int even = 0;
            int odd = 0;
            for (int i = 0; i < nums.length; i++) {
                if (nums[i]%2==0){
                    even = even + nums[i]*nums[i];
                    continue;
                }
                odd = odd + nums[i]*nums[i]*nums[i];
            }
            System.out.print(even+" "+odd);
            System.out.println();
        }
    }
}
