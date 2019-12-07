package org.zhuzhenxi.test.hangdian.h1089to2012.h2099;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * @Author:Zhuxixi
 * @Description:
 * @Date:Create in  2018/12/23 9:07 PM
 */
public class Main {
    public static void main(String[] args){
        Scanner in = new Scanner(System.in);
        while (in.hasNext()){
            int a = in.nextInt();
            int b = in.nextInt();
            if (a==0&&b==0){
                continue;
            }
            if (a>10000||b<10||b>100){
                continue;
            }
            a *=100;
            List<Integer> result = new ArrayList<>();
            for (int i = 0; i < 100; i++) {
                if ((a+i)%b==0){
                    result.add(i);
                }
            }
            for (int i = 0; i < result.size(); i++) {
                if (result.get(i)<10){
                    System.out.print("0"+result.get(i));
                }else {
                    System.out.print(result.get(i));
                }
                if (i+1==result.size()){
                    continue;
                }
                System.out.print(" ");
            }
            System.out.println();
        }
    }
}
