package org.zhuzhenxi.test.leetcode;

public class l852 {
    public static void main(String[] args){
        int[] mountain = {0,1,2,1,1};

        System.out.println(peakIndexInMountainArray(mountain));

    }

    public static int peakIndexInMountainArray(int[] A) {

        int max = A[0];
        int index = 0;
        for (int i = 1; i < A.length; i++) {
            if (A[i]>max){
                max = A[i];
                index = i;
            }else {
                break;
            }
        }
        return index;

    }
}
