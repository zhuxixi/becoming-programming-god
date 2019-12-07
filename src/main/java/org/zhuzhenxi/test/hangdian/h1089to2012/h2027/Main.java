package org.zhuzhenxi.test.hangdian.h1089to2012.h2027;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {

    private static Map<Character, Integer> kv = new LinkedHashMap<>();
    static {
        refresh();
    }

    public static void main(String[] args){
        Scanner in = new Scanner(System.in);
        while (in.hasNext()){
            int lines = in.nextInt();
            while (lines>0&&in.hasNext()){
                String words = in.nextLine();
                if ("".equals(words)){
                    continue;
                }
                char[] chars = words.toCharArray();
                for (int i = 0; i < chars.length; i++) {
                    Integer times = kv.get(chars[i]);
                    if(times!=null){
                        times = times+1;
                        kv.put(chars[i],times);
                    }
                }
                print();
                if (lines-1!=0){
                    System.out.println();
                }
                refresh();
                lines--;
            }

        }
    }

    private static void refresh(){
        kv.put('a',0);
        kv.put('e',0);
        kv.put('i',0);
        kv.put('o',0);
        kv.put('u',0);
    }
    private static void print(){
        for (Map.Entry<Character,Integer> entry:kv.entrySet()){
            System.out.println(entry.getKey()+":"+entry.getValue());
        }

    }

}
