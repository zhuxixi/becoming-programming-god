package org.zhuzhenxi.test.dcc;

import lombok.Data;

/**
 * 存储最大子数组的属性
 * 子数组结果
 * @Author:Zhuxixi
 * @Description:
 * @Date:Create in  2018/11/26 9:44 PM
 */

public class SubArray {
    //起始索引
    private int low;
    //结束索引
    private int high;
    //起始元素->结束元素的和
    private int maxValue;

    public int getLow() {
        return low;
    }

    public void setLow(int low) {
        this.low = low;
    }

    public int getHigh() {
        return high;
    }

    public void setHigh(int high) {
        this.high = high;
    }

    public int getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(int maxValue) {
        this.maxValue = maxValue;
    }

    public SubArray(int low1, int high1, int maxValue1){
        low = low1;
        high =high1;
        this.maxValue = maxValue1;

    }
}
