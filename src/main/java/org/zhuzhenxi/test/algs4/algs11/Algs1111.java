package org.zhuzhenxi.test.algs4.algs11;

public class Algs1111 {
    /**
     * 编写一段代码，打印出一个二维布尔数组的内容。其中，使用 * 表示真，空格表示假。打印出
     * 行号和列号。
     * @param args
     */
    public static void main(String[] args){
        boolean[][] a = new boolean[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (i==1&&j==2){
                    a[i][j] = false;
                    continue;
                }
                a[i][j] = true;
            }
        }

        a[0][0] = false;

        //打印第一行列号
        for (int i = 0; i < 3; i++) {
            if (i==0){
                System.out.print(" ");
            }
            System.out.print(" ");
            System.out.print(i+1);
            if (i ==2){
                continue;
            }
            System.out.print(" ");
        }
        System.out.println();

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (j==0){
                    System.out.print(i+1);
                }
                System.out.print(" ");
                if ( a[i][j]){
                    System.out.print("*");
                }else {
                    System.out.print("~");
                }
                if (j ==2){
                    continue;
                }
                System.out.print(" ");

            }
            System.out.println();
        }


    }
}
