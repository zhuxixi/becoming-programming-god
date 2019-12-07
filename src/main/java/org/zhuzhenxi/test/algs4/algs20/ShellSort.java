package org.zhuzhenxi.test.algs4.algs20;

/**
 * 100万个6位整数
 * 两个数组是否相等!true
 * 插入排序耗时:67304
 * 希尔排序耗时:131
 */
public class ShellSort {
    public static void main(String[] args) {
        int[] a = {0,7,9,8,3,6,5,4,2,1};
        shellSort(a);
        for (int i = 0; i < a.length; i++) {
            System.out.print(i);
            if (i==a.length-1){
                continue;
            }
            System.out.print(" ");
        }
    }

    public static void shellSort(int[] a){
        int h = a.length/2;
        while (h>=1){
            for (int i=h;i<a.length;i++){
                int ac = a[i];
                int j = i-h;
                while (j>=0&&ac<a[j]){
                    a[j+h] = a[j];
                    j = j-h;
                }
                a[j+h] = ac;
            }
            h = h/2;
        }
    }
}
