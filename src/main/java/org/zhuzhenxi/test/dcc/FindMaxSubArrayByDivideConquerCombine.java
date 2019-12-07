package org.zhuzhenxi.test.dcc;

/**
 * 分治法(递归)求解最大子数组问题
 * @Author:Zhuxixi
 * @Description:
 * @Date:Create in  2018/11/24 2:18 PM
 */
public class FindMaxSubArrayByDivideConquerCombine {
    //相等元素
    public static void main(String[] args) {

        int[] testArray = {13,-3,-25,20,-3,-16,-23,18,20,-7,12,-5,-22,15,-4,7};
//        int[] testArray = {-2,-8,-9,-10,11,12};

        SubArray result = findMaxSubArray(testArray,0,testArray.length-1);
        System.out.println("low:"+result.getLow());
        System.out.println("high:"+result.getHigh());
        System.out.println("value:"+result.getMaxValue());


    }

    /**
     * 寻找最大子数组
     * @param arr
     * @param start
     * @param end
     * @return
     */
    private static SubArray findMaxSubArray(int[] arr,int start ,int end){
        if (start==end){
            return new SubArray(start,end,arr[start]);
        }
        int middle = (start+end)/2;
        //最大左
        SubArray maxLeft = findMaxSubArray(arr,start,middle);
        //最大右
        SubArray maxRight = findMaxSubArray(arr,middle+1,end);
        //最大左+最大右
        SubArray maxCrossMiddle = findMaxCrossingSubArray(arr,start,middle,end);

        SubArray result = new SubArray(maxLeft.getLow(),maxLeft.getHigh(),maxLeft.getMaxValue());

        if (maxRight.getMaxValue()>result.getMaxValue()){
            result = maxRight;
        }
        if (maxCrossMiddle.getMaxValue()>result.getMaxValue()){
            result = maxCrossMiddle;
        }
        //最大左、最大右、最大左+最大右中，三选一，选最大
        return result;


    }



    /**
     * 必须跨越中点的最大子数组
     * @param arr
     * @param start
     * @param middle
     * @param end
     */
    private static SubArray findMaxCrossingSubArray(int[] arr, int start, int middle, int end) {
        int maxLeft = Integer.MIN_VALUE;
        int currentLeft = 0;
        int maxLeftIndex = 0;
        for (int i = middle; i > start; i--) {
            currentLeft = currentLeft + arr[i];
            if (currentLeft > maxLeft) {
                maxLeft = currentLeft;
                maxLeftIndex = i;
            }
        }
        int maxRight = Integer.MIN_VALUE;
        int currentRight = 0;
        int maxRightIndex = 0;
        for (int i = middle + 1; i < end; i++) {
            currentRight = currentRight + arr[i];
            if (currentRight > maxRight) {
                maxRight = currentRight;
                maxRightIndex = i;
            }
        }
        int finalSum = maxRight+maxLeft;
        SubArray result = new SubArray(maxLeftIndex, maxRightIndex, finalSum);

        //如果maxLeft 和 maxRight 都是初始值，说明没进for循环,也就是当前start 和 end 是连续的两个值，
        if (maxLeft == Integer.MIN_VALUE && maxRight == Integer.MIN_VALUE) {
            //两个元素比较的话，跨过中点的最大子数组就是两个元素的合
            finalSum = arr[start]+arr[end];
            result.setMaxValue(finalSum);
            result.setLow(start);
            result.setHigh(end);
            return result;
        }

        //如果maxLeft 和 maxRight 都不是初始值,那么直接将两个值相加即可
        if (maxLeft != Integer.MIN_VALUE && maxRight != Integer.MIN_VALUE) {
            finalSum = maxLeft + maxRight;
            result.setMaxValue(finalSum);
            return result;
        }
        //方法走到这里，说明 maxLeft 和 maxRight 有一个是初始值
        if (maxLeft != Integer.MIN_VALUE) {
            //如果start=0,middle=1,end=2,就会走到这里，此时不会进入右子数组的for循环
            System.out.println("maxRight是初始值,没有进入循环for(middle->end)");
            finalSum = maxLeft;
            result.setMaxValue(finalSum);
            return result;
        }

        System.out.println("maxLeft是初始值,没有进入循环for(middle->start)");
        finalSum = maxRight;
        result.setMaxValue(finalSum);
        return result;

    }


}
