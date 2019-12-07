package org.zhuzhenxi.test.algs4.algs20;

public class SelectionSort {
    public static void main(String[] args) {
        int[] a = {0,2,9,11,22,3,2,123,22,21,20,17,20};
        sort(a);
    }

    public static void sort(int[] a){
        //0
        //1
        int count = 0;

        for (int i = 0; i < a.length-1; i++) {
            int needToExchange = a[i];
            int min = a[i];
            int index = i;
            for (int j = i+1; j < a.length; j++) {
                if (a[j]<min){
                    min = a[j];
                    index = j;
                }
            }
            if (index==i+1||min==needToExchange){
                System.out.print(count);
                count++;
                System.out.print(" ");
                printArray(a,i,index);
                System.out.println();
                continue;
            }
            a[i] = min;
            a[index] = needToExchange;
            System.out.print(count);
            count++;
            System.out.print(" ");
            printArray(a,i,index);
            System.out.println();
        }

    }

    private static void printArray(int[] a,int iii,int index){
        for (int i = 0; i < a.length; i++) {
            if (iii!=0&&(i==iii||i==index)){
                System.out.print("(");

            }
            System.out.print(a[i]);
            if (iii!=0&&(i==iii||i==index)){
                System.out.print(")");

            }
            if (i==a.length-1){
                continue;
            }
            System.out.print(" ");
        }
    }
}
