package org.zhuzhenxi.test.algs4.algs11;

public class Algs1113 {
    public static void main(String[] args) {
        int[][] a = new int[4][4];
        int b = 9;
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < a.length; j++) {
                a[i][j] = b;
                b--;
                if (b<0){
                    b=9;
                }
            }
        }
        print(a);

        System.out.println();

        for (int i = 0; i < a.length; i++) {
            for (int j = i; j < a.length; j++) {
                int temp = a[i][j];
                a[i][j] = a[j][i];
                a[j][i] = temp;
            }
        }
        print(a);

    }

    private static void print(int[][] a) {
        //列号
        for (int i = 0; i < a.length; i++) {
            if (i == 0) {
                System.out.print(" ");
            }
            System.out.print(" ");
            System.out.print(i);
        }
        System.out.println();

        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < a.length; j++) {
                if (j == 0) {
                    System.out.print(i);
                }
                System.out.print(" ");
                System.out.print(a[i][j]);

            }
            System.out.println();
        }
    }
}
