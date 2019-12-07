package org.zhuzhenxi.test.algs4.algs11;

public class Algs1115 {
    public static void main(String[] args){
        int[] a = {0,1,2,3,4,5,6,7,8,9,0,1,2,1,1,3,3,2,2};
        int[] b = histogram(a,3);
        int sum = 0;
        for (int i = 0; i < b.length; i++) {
            System.out.print(b[i]+" ");
            sum += b[i];
        }
        System.out.println();

        System.out.println(sum);
        System.out.println(a.length);

    }

    public static int[] histogram(int[] a,int m){
        int[] result = new int[m];

        for (int i = 0; i < a.length; i++) {
            if (a[i] > m - 1) {
                continue;
            }
            //m的第 i个元素的值为整数i在参数数组中出现的次数

            result[a[i]] += 1;

        }
        return result;
    }



}
