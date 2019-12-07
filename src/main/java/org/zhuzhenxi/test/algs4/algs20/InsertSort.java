package org.zhuzhenxi.test.algs4.algs20;

/**
 * 稳定排序
 */
public class InsertSort {
    public static void main(String[] args) {
        int[] a = {0,7,9,8,3,6,5,4,2,1};
        sort(a);
    }

    public static void sort(int[] a){
        //0
        //1
        int count = 0;
        for (int i = 1; i < a.length; i++) {
//            System.out.println("----------------------------------------------------------");

            int aaaa = a[i];
//            System.out.print("当前处理 ("+aaaa+")  ");
            int j = i-1;
//            System.out.print("此时的i ("+i+")  ");
//            System.out.print("此时的j ("+j+")  ");

//            printArray(a);
//            System.out.println();
            //因为数组左边是有序数组，所以跟a[i]>a[j]，说明a[i]大于左边的所有元素
            /**
             * 如果a[i]<a[j]，就要逐个比较了，每次比较结束，j=j-1
             * 直到a[i]>a[j]
             * 此时a[i]应该放在a[j]的右边的位置，因为a[i]>[aj]
             * 所有a[i] = a[j+1]
             */
            while (j>=0&&aaaa<a[j]){
                a[j+1] = a[j];
                j--;
//                System.out.print("当前处理 ("+aaaa+")  ");
//                System.out.print("此时的i ("+i+")  ");
//
//                System.out.print("此时的j ("+j+")  ");
//                printArray(a);
//                System.out.println(" 我在while里");
            }
            a[j+1] = aaaa;
//            System.out.print("当前处理 ("+aaaa+")  ");
//            System.out.print("此时的i ("+i+")  ");
//
//            System.out.print("此时的j ("+j+")  ");
//            printArray(a);
//            System.out.println(" 我重新赋值了");

        }

    }

    private static void printArray(int[] a){
        for (int i = 0; i < a.length; i++) {
            System.out.print(a[i]);
            if (i==a.length-1){
                continue;
            }
            System.out.print(" ");
        }
    }

}
