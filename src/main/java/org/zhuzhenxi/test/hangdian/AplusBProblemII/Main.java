package org.zhuzhenxi.test.hangdian.AplusBProblemII;

import java.io.BufferedInputStream;
import java.util.Scanner;


public class Main {

    /**
     * 如果输入003
     * char[0] 是0，所以大数组相加需要数组反向遍历相加
     * @param args
     */
    public static void main(String[] args) {

        Scanner in = new Scanner(new BufferedInputStream(System.in));
        while (in.hasNextLine()) {
            //记录计算到第几行
            int index = 1;
            //输入一个整数n，表明下面将会有n行，每行包含数字空格数字
            int linesNum = Integer.parseInt(in.nextLine());
            //记录一下行数
            int mirror = linesNum;
            //如果输入的数字大于0 并且 输入后面的行
            while (linesNum > 0&&in.hasNextLine()) {
                String str = in.nextLine();
                String[] ab = str.split(" ");
                //第一个数字a
                char[] a1char = ab[0].toCharArray();
                //第二个数字b
                char[] b1char = ab[1].toCharArray();
                //去掉数字 a b的前导0 例如 0009 需要处理成 9
                a1char = filter0(a1char);
                b1char = filter0(b1char);
                //记录数字a b位数
                int a1charLenth = a1char.length;
                int b1charLenth = b1char.length;
                //如果输入的两个字符串都是00000 0000 ，就直接输出0
                if (a1charLenth==0&&b1charLenth==0){
                    printResult(index,mirror,ab,0+"");
                    index++;
                    linesNum--;
                    continue;
                }
                //两个字符串长度差值
                int a1largerThanb1 = a1charLenth - b1charLenth;
                //先声明结果
                char[] resultchar = null;
                //比较数组长短，我们将短数组加到长的上面去，这样遍历一次长数组，长数组就是结果
                if (a1largerThanb1>0) {
                    resultchar = sum(a1char,b1char,a1largerThanb1);
                } else {
                    resultchar = sum(b1char,a1char,Math.abs(a1largerThanb1));
                }
                //转换成字符串打印
                String resultstr = new String(resultchar);
                printResult(index,mirror,ab,resultstr);
                index++;
                linesNum--;
            }
        }

    }

    /**
     * 打印结果
     * @param index 处理到第几行
     * @param mirror 总行数
     */
    private static void printResult(int index,int mirror,String[] ab,String resultstr) {
        System.out.println("Case " + index + ":");
        System.out.println(ab[0] + " + " + ab[1] + " = " + resultstr);
        //如果当前行数已经是最后一行了，就不需要打印空行
        if (index!=mirror){
            System.out.println();
        }
    }

    /**
     * 求和
     * @param large 长度更长的数组
     * @param shorter 长度短的数组
     * @param cut 数组长度差值的绝对值
     * @return
     */
    private static char[] sum(char[] large,char[] shorter,int cut){
        //保存当前的进位
        int forward = 0;
        //逆序遍历长度更长的数组 因为字符串转char数组之后 是从左到右排列的 9900 0099 large[0] = 9 shorter[0]=0
        for (int i = large.length-1; i >= 0; i--) {
            int sum = forward;
            //从短数组取值需要减去差值,否则就不需要取了，仅处理进位
            if ((i-cut)<0){
            }else {
                //如果短数组还有没处理完的数，取出并计算 sum = sum + b，需要将char转换成int
                int b = char2int(shorter[i-cut]);
                sum += b;
            }
            //取出对应的长数组，将char转换成int
            int a = char2int(large[i]);
            //求和
            sum += a;
            //除10取商，获得新的进位
            forward = sum / 10;
            //当前位数保存除10的余数，也就是对10进行模运算
            large[i] = int2char(sum % 10);
        }
        //此时进位如果>0 说明最左边有进位 例如，9999+1 = 10000
        if (forward>0){
            char[] result = new char[large.length+1];
            result[0] = int2char(forward);
            for (int i = 1; i < result.length; i++) {
                result[i] = large[i-1];
            }
            large = result;
        }
        return large;
    }

    private static int char2int(char input){
        return (int)input - (int)'0';
    }

    private static char int2char(int input){
        return(char)(input + '0');
    }

    /**
     * 去掉前导0
     * @param chars
     * @return
     */
    private static char[] filter0(char[] chars){
        int num0 = 0;
        for (int i = 0; i < chars.length; i++) {
            if(char2int(chars[i])==0){
                num0++;
            }else {
                break;
            }

        }
        if (num0==0){
            return chars;
        }
        char []result0 = new char[chars.length-num0];
        for (int i = num0; i < chars.length; i++) {
            result0[i-num0] = chars[i];
        }
        return result0;
    }
}
