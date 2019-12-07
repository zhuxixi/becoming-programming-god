package org.zhuzhenxi.test.hangdian.h1089to2012.h2031;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {

    public static final Map<Integer, Integer> KV029 = new HashMap<>();

    static {
        KV029.put(0, 0);
        KV029.put(1, 1);
        KV029.put(2, 2);
        KV029.put(3, 3);
        KV029.put(4, 4);
        KV029.put(5, 5);
        KV029.put(6, 6);
        KV029.put(7, 7);
        KV029.put(8, 8);
        KV029.put(9, 9);
    }

    public static final Map<Integer, String> KVA2Z = new HashMap<>();

    static {
        KVA2Z.put(10, "A");
        KVA2Z.put(11, "B");
        KVA2Z.put(12, "C");
        KVA2Z.put(13, "D");
        KVA2Z.put(14, "E");
        KVA2Z.put(15, "f");
    }


    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        while (in.hasNext()) {
            int num = in.nextInt();
            boolean isNative = false;
            if (num<0){
                isNative = true;
                num = num*-1;
            }
            int jinzhi = in.nextInt();
            if (jinzhi<2||jinzhi>16||jinzhi==10){
                continue;
            }
            String result = convert(num,jinzhi);
            if (isNative){
                System.out.print("-");
            }
            System.out.println(result);
        }
    }

    public static String convert(int num,int jinzhi){
        if (num<jinzhi){
            StringBuilder newInt = new StringBuilder();
            newInt.append(pickNum(num));
            return newInt.toString();
        }

        return new StringBuilder().append(convert(num/jinzhi,jinzhi)).append(convert(num%jinzhi,jinzhi)).toString();
    }

    public static String pickNum(int num){
        if (num<10){
            return String.valueOf(KV029.get(num));
        }
        return KVA2Z.get(num);
    }
}
