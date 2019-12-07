package org.zhuzhenxi.test.sort;

import org.zhuzhenxi.test.random.RandomUtil;

import java.util.ArrayList;
import java.util.List;

public class QuickSortNumber {
    public static void main(String[] args) {
        List<Integer> testArray = new ArrayList<>(1000000000);

        for (int i = 0; i < 1000000000; i++) {
            testArray.add(RandomUtil.generateRandomNumber());
        }
        System.out.println("排序前"+testArray);
        long start = System.currentTimeMillis();
        testArray = quickSort(testArray);
        long end = System.currentTimeMillis();
        System.out.println("快速排序耗时："+(end-start));//200条3毫秒，2万=50毫秒，20万500毫秒，200万=3秒
        System.out.println("排序后"+testArray);
        testArray = testArray.subList(0,99);
        System.out.println("排序后"+testArray);



    }


    private static List<Integer> quickSort(List<Integer> array){
        if (array.size()<101){
//            System.out.println("当前区间"+array);
            return array;
        }
        int pivot0 = array.get(0);
        List<Integer> left = new ArrayList<>(1000000000);
        List<Integer> pivot= new ArrayList<>(1000000000);
        List<Integer> right = new ArrayList<>(1000000000);

        for (int i = 0; i < array.size(); i++) {
            Integer current = array.get(i);
            if (current<pivot0){
                left.add(current);
                continue;
            }
            if (current==pivot0){
                pivot.add(current);
                continue;
            }
            right.add(current);

        }
        List<Integer> left1 = quickSort(left);
        List<Integer> right1 = quickSort(right);

        left1.addAll(pivot);
        left1.addAll(right1);
        return left1;
    }

}
