package org.zhuzhenxi.test.algs4.algs20;

import java.util.Arrays;

public class MergeSort {

    private static int[] COPY;
    public static void main(String[] args) {
        int[] a = {0,7,9,8,3,6,5,4,2,1,12,15,10,14,13,11};
        COPY = new int[a.length];
        int low = 0;
        int mid = a.length/2;
        int high = a.length-1;
//        merge(a,low,mid,high);
        System.out.println("当前low= 0,当前mid= 0,当前high= 0,当前k= 0,当前i= 0,当前j= 1 当前数组a: 0   1   2   3   4   5   6   7   8   9   10   11   12   13   14   15   当前数组拷贝:0 1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 ");
        mergeSort(a,low,high);

        for (int i = 0; i < a.length; i++) {
            System.out.print(a[i]);
            System.out.print(" ");
        }
    }
    public static void mergeSort(int[] a,int low,int high){
        if (low>=high){
            return;
        }
        int mid = low+(high-low)/2;
        System.out.println("当前low="+low+",当前mid="+mid+",当前high="+high);

        mergeSort(a,low,mid);
        mergeSort(a,mid+1,high);
        merge(a,low,mid,high);
    }


    private static void merge(int[] a,int low,int mid,int high){
        int i = low;
        int j = mid+1;
        for (int k = low; k <=high; k++) {
             COPY[k] = a[k];
        }
        for (int k = low; k <= high; k++) {
            System.out.print("当前low="+format(low)+",当前mid="+format(mid)+",当前high="+format(high)+",当前k="+format(k)+",当前i="+format(i)+",当前j="+format(j));
            if (i>mid){
                a[k] = COPY[j++];
            }else if (j>high){
                a[k] = COPY[i++];
            }else if (COPY[j]>COPY[i]){
                a[k] = COPY[i++];
            }else {
                a[k] = COPY[j++];
            }
            System.out.print(" 当前数组a:");
            for (int lala = 0; lala < a.length; lala++) {
                if (lala == k){
                    System.out.print("(");
                }else {
                    System.out.print(" ");

                }
                System.out.print(a[lala]);
                if (lala == k){
                    System.out.print(")");
                }else {
                    System.out.print(" ");

                }
                System.out.print(" ");
            }
            System.out.print(" 当前数组拷贝:");
            for (int lala = 0; lala < a.length; lala++) {

                System.out.print(COPY[lala]);
                System.out.print(" ");
            }
            System.out.println();
        }
    }


    private static String  format(int a){
        return a<10?(" "+a):String.valueOf(a);
    }
}
