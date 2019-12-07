package org.zhuzhenxi.test.hangdian.h1089to2012.h1018;

import java.util.Scanner;

/**
 * 求N的阶乘的值的位数
 * 斯特林公式 log10(n!)=1.0/2*log10(2*PI*n)+n*log10(n/e)；
 * @Author:Zhuxixi
 * @Description:
 * @Date:Create in  2018/12/23 8:02 PM
 */
public class Main {
    public static void main(String[] args){
        Scanner in = new Scanner(System.in);
        while (in.hasNext()){
            int nums = in.nextInt();
            while (nums>0&&in.hasNext()){
                //32位int可以表示 40亿
                int a = in.nextInt();
                Double result = 1.0/2*Math.log10(2*Math.PI*a)+a*Math.log10(a/Math.E)+1;
                System.out.println(result.intValue());
                nums--;
            }
        }
    }
}
