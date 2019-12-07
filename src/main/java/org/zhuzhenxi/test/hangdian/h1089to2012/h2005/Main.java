package org.zhuzhenxi.test.hangdian.h1089to2012.h2005;

import java.util.Scanner;


public class Main {
    public static void main(String[] args) {
        Scanner in=new Scanner(System.in);
        while (in.hasNext()) {
            String ymd=in.next();

            getday(ymd);

        }
    }

    private static void getday(String ymd){
        int year=Integer.parseInt(ymd.substring(0, ymd.indexOf("/")));
        ymd=ymd.substring(ymd.indexOf("/")+1);
        int month=Integer.parseInt(ymd.substring(0, ymd.indexOf("/")));
        int day=Integer.parseInt(ymd=ymd.substring(ymd.indexOf("/")+1));

        int[] d={31,28,31,30,31,30,31,31,30,31,30,31};

        if((year%4==0||year%100!=0)&&year%400==0){
            d[1]=29;
        }

        int dsum=0;
        for(int i=0;i<month-1;i++){
            dsum+=d[i];
        }

        System.out.println(dsum+day);
    }
}