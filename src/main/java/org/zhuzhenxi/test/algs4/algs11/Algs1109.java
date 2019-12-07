package org.zhuzhenxi.test.algs4.algs11;

public class Algs1109 {
    /**
     * 一个将整数转换为二进制字符串的算法
     * 编写一段代码，将一个正整数 N 用二进制表示并转换为一个 String 类型的值 s。
     * @param args
     */
    public static void main(String[] args){
        int a = 9;
        String b = "";
        for (int i = a; i >0 ; i/=2) {
            b = i%2+b;
        }
        System.out.println(b);
    }
}
