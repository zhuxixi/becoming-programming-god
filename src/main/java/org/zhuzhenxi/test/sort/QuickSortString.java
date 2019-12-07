package org.zhuzhenxi.test.sort;

import org.zhuzhenxi.test.random.RandomUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QuickSortString {
    private static Map<Character,Integer> index = new HashMap<>();

    private static final char[] CODES = { '0','1','2','3','4','5','6','7','8','9',
            'A','B','C','D','E','F','G','H','I','J',
            'K','L','M','N','O','P','Q','R','S','T',
            'U','V','W','X','Y','Z','a','b','c','d',
            'e','f','g','h','i','j','k','l','m','n',
            'o','p','q','r','s','t','u','v','w','x',
            'y','z'};
    static {
        for (int i = 0; i < CODES.length; i++) {
            index.put(CODES[i],i);
        }
    }

    public static void main(String[] args) {
        List<String> testArray = new ArrayList<>();

        for (int i = 0; i < 30000; i++) {
            testArray.add(RandomUtil.generateRandomString(5));
        }
        for (int i = 0; i < 12; i++) {
            testArray.add(RandomUtil.generateRandomString(4));
        }
        for (int i = 0; i < 7; i++) {
            testArray.add(RandomUtil.generateRandomString(8));
        }
//        System.out.println("排序前："+testArray);
        long start = System.currentTimeMillis();
        testArray = quickSort(testArray);
        long end = System.currentTimeMillis();
        System.out.println("快速排序耗时："+(end-start));//200条3毫秒，2万=50毫秒，20万500毫秒，200万=3秒
//        System.out.println("排序后："+testArray);


    }


    private static List<String> quickSort(List<String> array){
        if (array.size()<2){
            return array;
        }
        String pivot0 = array.get(0);
        char[] pivot0CharArray = pivot0.toCharArray();

        List<String> left = new ArrayList<>();
        List<String> pivot= new ArrayList<>();
        List<String> right = new ArrayList<>();

        for (int i = 0; i < array.size(); i++) {
            String current = array.get(i);
            char[] currentCharArray = current.toCharArray();
            if (current.equals(pivot0)){
                pivot.add(current);
                continue;
            }
            if (currentCharArray.length<pivot0CharArray.length){
                left.add(current);
                continue;
            }
            if (currentCharArray.length>pivot0CharArray.length){
                right.add(current);
                continue;
            }

            for (int j = 0; j < currentCharArray.length; j++) {
                if (index.get(currentCharArray[j])<index.get(pivot0CharArray[j])){
                    left.add(current);
                    break;
                }
                if (index.get(currentCharArray[j])>index.get(pivot0CharArray[j])){
                    right.add(current);
                    break;
                }
            }

        }
        List<String> left1 = quickSort(left);
        List<String> right1 = quickSort(right);

        left1.addAll(pivot);
        left1.addAll(right1);
        return left1;
    }





}
