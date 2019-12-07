package org.zhuzhenxi.test.algs4.algs20;

import java.util.Arrays;

public class QuickSort {
    public static void main(String[] args) {
        int[] a = {0,7,9,8,3,6,5,4,2,1,12,15,10,14,13,11};
        quickSort(a,0,a.length-1);
        System.out.println(Arrays.toString(a));
    }

    public static void quickSort(int[] a,int low,int high){
        System.out.println("切分开始:low="+low+",high="+high);
        if (low>=high){
            System.out.println("不用切分:low>=high,low="+low+",high="+high);
            return;
        }

        int j = partition(a,low,high);
        System.out.println("切分结束:j="+j);
        quickSort(a,low,j-1);
        quickSort(a,j+1,high);

    }
    private static int partition(int[]a ,int low,int high){
        System.out.println("循环开始:low="+low+",high="+high);
        int i = low;
        int j = high+1;
        int key = a[low];
        while (true){

            while (a[++i]<key){

            }
            while (a[--j]>key){

            }
            if (i>=j){
                break;
            }
            exch(a,i,j);
        }
        exch(a,low,j);
        System.out.println("循环结束:i="+i+",j="+j);
        return j;
    }

    private static void exch(int[] a,int i,int j){
        int temp = a[i];
        a[i] = a[j];
        a[j] = temp;
        System.out.println(Arrays.toString(a));
    }

    private static boolean lessI(int[] a,int key,Integer i){
        System.out.print("在找左边的:i="+i);
        int mubiao = ++i;
        System.out.print("++i="+i+",a[++i]="+a[mubiao]);
        System.out.println();

        return a[mubiao]<key;
    }
    private static boolean lessJ(int[] a,int key,int j){
        System.out.print("在找右边的:j="+j);
        int mubiao = --j;
        System.out.print("--j="+j+",a[--j]="+a[mubiao]);
        System.out.println();
        return key<a[mubiao];
    }
}
