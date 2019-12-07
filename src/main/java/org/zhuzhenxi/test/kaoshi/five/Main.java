package org.zhuzhenxi.test.kaoshi.five;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        while (sc.hasNext()){
            String line = sc.nextLine();
            String[] words = line.split(" ");
            Map<String,Integer> result = new HashMap<>();
            for (int i = 0; i < words.length; i++) {
                String word = words[i];
                word = word.toLowerCase();
                if (result.get(word)==null){
                    result.put(word,1);
                }else {
                    result.put(word,result.get(word)+1);
                }
            }

            int max = 0;
            String resultStr ="";
            for (Map.Entry<String,Integer> entry:result.entrySet()){
                int a = entry.getValue();
                if (a>max){
                    max = a;
                    resultStr = entry.getKey();
                }
            }
            System.out.println(resultStr);

        }
    }
}
