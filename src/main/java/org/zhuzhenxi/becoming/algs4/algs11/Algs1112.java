package org.zhuzhenxi.becoming.algs4.algs11;

public class Algs1112 {
    /**
     * 没影响的
     * @param args
     */
    public static void main(String[] args){
        int[] a = new int[10];
        for (int i = 0; i < 10; i++){
            a[i] = 9 - i;
        }
        for (int i = 0; i < 10; i++){
            a[i] = a[a[i]];
        }
        for (int i = 0; i < 10; i++){
            System.out.println(i);
        }
    }
}
