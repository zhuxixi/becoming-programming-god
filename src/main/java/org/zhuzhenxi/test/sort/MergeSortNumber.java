package org.zhuzhenxi.test.sort;

import com.alibaba.fastjson.JSON;

import java.util.Arrays;

/**
 * @Author:Zhuxixi
 * @Description:
 * @Date:Create in  2018/11/21 9:57 PM
 */
public class MergeSortNumber {
    public static void main(String[] args) {
        Integer[] testArray = {45,21,133,27,72,84,115,6};

        System.out.println("排序前"+ JSON.toJSONString(testArray));
        long start = System.currentTimeMillis();
        mergeSort(testArray,0,7);
        long end = System.currentTimeMillis();
        //200条3毫秒，2万=50毫秒，20万500毫秒，200万=3秒
        System.out.println("归并排序耗时："+(end-start));
        System.out.println("排序后"+JSON.toJSONString(testArray));



    }


    /**
     * 归并排序
     * 将一个数组递归地均分成两部分，分别复制为a和b两个数组
     * 从a和b中选出最小的放入原数组的起始位置，如果有a或b其中有一个数组的元素先被拿光，那么直接把剩下的那个数组里的剩余元素直接放入原数组就排好序了
     * 递归并均分两个数组直到数组只有一个元素，此时递归开始回升
     * 递归回升后每次都是重排两个有序数组
     * @param param
     */
    private static void mergeSort(Integer[] param,int start,int end){
        if (start<end){
            mergeSort(param,start,(end+start)/2);
            mergeSort(param,((end+start)/2)+1,end);
            merge(param,start,(end+start)/2,end);
        }

    }

    private static void merge(Integer[] param,int start ,int middle, int end){
        Integer[] a = {};
        Integer[] b = {};
        for (int i = 0; i < (middle-start)+1; i++) {
            a = Arrays.copyOf(a,a.length+1);
            a[i] = param[start+i];
        }
        for (int i = 0; i < (end-middle); i++) {
            b = Arrays.copyOf(b,b.length+1);
            b[i] = param[middle+i+1];
        }
        int i = 0;
        int j = 0;
        int k = start;
        //如果 其中一个数组先拿完了，就把剩余的填进去
        for (; k < end+1; k++) {
            //a 拿完了就继续复制b
            if(i==a.length){
                param[k] = b[j];
                j = j+1;
                continue;
            }
            //b 拿完了就继续复制a
            if (j==b.length){
                param[k] = a[i];
                i = i+1;
                continue;
            }
            if (a[i]<b[j]){
                param[k] = a[i];
                i = i+1;
                continue;
            }
            param[k] = b[j];
            j = j+1;

        }



    }
}
