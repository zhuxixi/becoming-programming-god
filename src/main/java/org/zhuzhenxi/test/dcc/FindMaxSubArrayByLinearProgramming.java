package org.zhuzhenxi.test.dcc;

/**
 * 其实暴力求解稍微改一下就可以提升效率
 * 线性规划求解最大子数组
 * 时间复杂度 n
 * @Author:Zhuxixi
 * @Description:
 * @Date:Create in  2018/11/26 10:09 PM
 */
public class FindMaxSubArrayByLinearProgramming {
    public static void main(String[] args) {

        int[] testArray = {13,-3,-25,20,-3,-16,-23,18,20,-7,12,-5,-22,15,-4,7};
//        int[] testArray = {1,-18,17,-19,-110,-11,-11};

        SubArray result = findMaxSubArray(testArray);
        System.out.println("low:"+result.getLow());
        System.out.println("high:"+result.getHigh());
        System.out.println("value:"+result.getMaxValue());


    }

    /**
     * 线性规划寻找最大子数组
     *
     * @param arr
     * @return
     */
    private static SubArray findMaxSubArray(int[] arr){
        int lastStart = 0;
        int lastEnd = 0;
        //当前最大值
        int lastCurrentMaxValue = Integer.MIN_VALUE;

        int start = 0;
        int end = 0;
        //当前最大值
        int currentMaxValue = Integer.MIN_VALUE;

        //从数组第一个数开始，判断arr[1]~arr[n]的合
        for (int i = 0; i < arr.length; i++) {
            int a = arr[i];
            int j = i;
            //先判断一下自己是否比当前大
            if (a>currentMaxValue){
                currentMaxValue=a;
                start = i;
                end = j;
            }
            j++;
            while (j<arr.length){
                a += arr[j];
                if (a>currentMaxValue){
                    currentMaxValue=a;
                    start = i;
                    end = j;
                }

                j++;

            }
            if (a>lastCurrentMaxValue){
                lastStart = i;
                lastEnd = j;
                lastCurrentMaxValue = a ;
            }

        }
        return new SubArray(lastStart,lastEnd,currentMaxValue);

    }
}
