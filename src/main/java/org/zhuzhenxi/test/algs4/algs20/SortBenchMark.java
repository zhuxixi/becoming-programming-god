package org.zhuzhenxi.test.algs4.algs20;

import org.zhuzhenxi.test.random.RandomUtil;

import java.util.Arrays;

public class SortBenchMark {
    public static void main(String[] args) {
        int[] a = new int[1000000];
        for (int i = 0; i < a.length; i++) {
            a[i] = RandomUtil.generateRandomNumber();
        }
        int[] b = Arrays.copyOf(a,a.length);
        boolean isEqual = true;
        for (int i = 0; i < a.length; i++) {
            if (a[i]!=b[i]){
                isEqual = false;
            }
        }

        System.out.println("两个数组是否相等!"+isEqual);
        long startInsertSort = System.currentTimeMillis();
        InsertSort.sort(a);
        long endInsertSort = System.currentTimeMillis();
        System.out.println("插入排序耗时:"+(endInsertSort-startInsertSort));
        long startShellSort = System.currentTimeMillis();
        ShellSort.shellSort(b);
        long endShellSort = System.currentTimeMillis();
        System.out.println("希尔排序耗时:"+(endShellSort-startShellSort));


    }
}
