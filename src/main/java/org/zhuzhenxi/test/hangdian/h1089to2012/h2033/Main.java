package org.zhuzhenxi.test.hangdian.h1089to2012.h2033;

import java.util.Scanner;

public class Main {
    public static void main(String[] args){
        Scanner in = new Scanner(System.in);
        while (in.hasNext()){
            int lines = in.nextInt();
            while (lines>0){
                //AH,AM,AS,BH,BM,BS
                int ah = in.nextInt();
                int am = in.nextInt();
                int as = in.nextInt();
                int bh = in.nextInt();
                int bm = in.nextInt();
                int bs = in.nextInt();
                int step = 0;

                int cs = (as+bs)%60;

                step = (as+bs)/60;
                int cm = (am+bm+step)%60;
                step = (am+bm+step)/60;
                int ch  = step + ah+bh;
                System.out.println(ch+" "+cm+" "+cs);
                lines--;
            }
        }
    }
}
