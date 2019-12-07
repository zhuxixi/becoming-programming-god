package org.zhuzhenxi.test.kaoshi.four;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        while (sc.hasNext()){
            int lines = sc.nextInt();
            List<Double> result = new ArrayList<>();
            while (lines>0&&sc.hasNext()){
                double a = sc.nextDouble();
                result.add(a);
                lines--;
            }
            Collections.sort(result);
            double d = result.get(result.size()-1)-result.get(0);
            DecimalFormat df = new DecimalFormat("0.00");
            System.out.println(df.format(d));
        }
    }
}
