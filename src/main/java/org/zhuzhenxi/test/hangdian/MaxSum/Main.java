package org.zhuzhenxi.test.hangdian.MaxSum;

import java.io.BufferedInputStream;
import java.util.Scanner;

/**
 * 杭电10003 求解最大子数组
 * @Author:Zhuxixi
 * @Description:
 * @Date:Create in  2018/12/10 12:40 AM
 */
public class Main {

    //13 -3 -25 20 -3 -16 -23 18 20 -7 12 -5 -22 15 -4 7
    public static void main(String[] args){
        Scanner in = new Scanner(new BufferedInputStream(System.in));
        while (in.hasNextLine()) {
            //记录计算到第几行
            int index = 1;
            //输入一个整数n，表明下面将会有n行，每行包含数字空格数字
            int linesNum = Integer.parseInt(in.nextLine());
            //记录一下行数
            int mirror = linesNum;
            //如果输入的数字大于0 并且 输入后面的行
            while (linesNum > 0 && in.hasNextLine()) {
                String str = in.nextLine();
                String[] array = str.split(" ");
                int[] input = toIntArray(array);
                NewSubArray result = findMaxSub(input,0,input.length-1);
                printResult(index,mirror,result);
                linesNum--;
                index++;

            }
        }
    }

    /**
     * 打印结果
     * @param index 处理到第几行
     * @param mirror 总行数
     */
    private static void printResult(int index,int mirror,NewSubArray result) {
        System.out.println("Case " + index + ":");
        System.out.println(result.getValue() + " " + result.getStart() + " " + result.getEnd());
        //如果当前行数已经是最后一行了，就不需要打印空行
        if (index!=mirror){
            System.out.println();
        }
    }
    private static int[] toIntArray(String[] array){
        int[] result = new int[array.length-1];
        for (int i = 1; i < array.length; i++) {
            result[i-1] = Integer.parseInt(array[i]);
        }
        return result;
    }

    private static   NewSubArray findMaxSub(int[] input,int start,int end){
        if (start==end){
            return new NewSubArray(input[start],start,end);
        }

        int middle = ((start+end)/2);

        NewSubArray maxLeft = findMaxSub(input,start,middle);
//        System.out.println("left"+JSON.toJSONString(maxLeft));
        NewSubArray maxRight = findMaxSub(input,middle+1,end);
//        System.out.println("right"+JSON.toJSONString(maxRight));

        NewSubArray maxCrossMiddle = findMaxCrossMiddle(input,start,middle,end);
//        System.out.println("middle"+JSON.toJSONString(maxCrossMiddle));

        //三个比大小
        NewSubArray result = maxLeft.getValue()>maxRight.getValue()?maxLeft:maxRight;
        result = result.getValue()>maxCrossMiddle.getValue()?result:maxCrossMiddle;
//        System.out.println("本次最大"+JSON.toJSONString(result));

        return result;
    }

    private static NewSubArray findMaxCrossMiddle(int[] input,int start,int middle,int end){
        int maxLeft = Integer.MIN_VALUE;
        int sumLeft = Integer.MIN_VALUE;
        int leftIndex = Integer.MIN_VALUE;
        for (int i = middle; i >=start ; i--) {
            //如果是首次遍历，直接跳出
            if (maxLeft==Integer.MIN_VALUE){
                maxLeft = input[i];
                leftIndex = i;
                sumLeft = input[i];
                continue;
            }
            sumLeft = input[i]+sumLeft;
            if (sumLeft>maxLeft){
                maxLeft = sumLeft;
                leftIndex = i;
            }
        }

        int maxRight = Integer.MIN_VALUE;
        int sumRight = Integer.MIN_VALUE;
        int rightIndex = Integer.MIN_VALUE;

        for (int i = middle+1; i <= end ; i++) {
            //如果是首次遍历，直接跳出
            if (maxRight==Integer.MIN_VALUE){
                maxRight = input[i];
                rightIndex = i;
                sumRight = i;
                continue;
            }
            sumRight = input[i]+sumRight;
            if (sumRight>maxLeft){
                maxRight = sumRight;
                rightIndex = i;
            }
        }

        NewSubArray result = null;
        //说明start=end
        if (maxLeft==Integer.MIN_VALUE&&maxRight==Integer.MIN_VALUE){
            result = new NewSubArray(input[start]+input[end],start,end);

//            System.out.println("左右都是初始值"+JSON.toJSONString(result));
            return result;
        }

        if (maxLeft!=Integer.MIN_VALUE&&maxRight==Integer.MIN_VALUE){
            result = new NewSubArray(maxLeft,start,end);
//            System.out.println("右都是初始值"+JSON.toJSONString(result));

            return result;
        }
        if (maxLeft==Integer.MIN_VALUE&&maxRight!=Integer.MIN_VALUE){

            result = new NewSubArray(maxRight,middle,rightIndex);
//            System.out.println("左都是初始值"+JSON.toJSONString(result));

            return result;
        }

        result = new NewSubArray(maxLeft+maxRight,leftIndex,rightIndex);
//        System.out.println("左右都不是初始值"+JSON.toJSONString(result));

        return result;






    }

    public static class NewSubArray{
        private int value;
        private int start;
        private int end;

        public NewSubArray(int value1,int start1,int end1) {
            value = value1;
            start = start1;
            end = end1;
        }

        public int getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = value;
        }

        public int getStart() {
            return start;
        }

        public void setStart(int start) {
            this.start = start;
        }

        public int getEnd() {
            return end;
        }

        public void setEnd(int end) {
            this.end = end;
        }
    }
}
