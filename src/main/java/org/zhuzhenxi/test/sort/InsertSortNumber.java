package org.zhuzhenxi.test.sort;

import com.alibaba.fastjson.JSON;

/**
 * @Author:Zhuxixi
 * @Description:
 * @Date:Create in  2018/11/19 10:32 PM
 */
public class InsertSortNumber {

    public static void main(String[] args) {
        Integer[] testArray = {40,23,33,2,21,12,32,34};

        System.out.println("排序前"+ JSON.toJSONString(testArray));
        long start = System.currentTimeMillis();
        insertSort(testArray);
        long end = System.currentTimeMillis();
        //200条3毫秒，2万=50毫秒，20万500毫秒，200万=3秒
        System.out.println("插入排序耗时："+(end-start));
        System.out.println("排序后"+JSON.toJSONString(testArray));



    }


    /**
     * 插入排序
     * 将数组分成 0与1~length两部分
     * 两个循环嵌套
     * 外层循环遍历 1~length部分 从1开始拿数，然后和0的元素比较大小，如果需要插入，需要挪动内层循环的数。
     *
     * @param param
     */
    private static void insertSort(Integer[] param){
        for (int i = 1; i < param.length; i++) {
            Integer current = param[i];
            int j = i-1;
            while (j>=0&&param[j]>current){
                param[j+1] = param[j];
                j = j-1;
            }
            param[j+1] = current;
        }

    }
}
