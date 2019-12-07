package org.zhuzhenxi.test.leetcode;

import com.alibaba.fastjson.JSON;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class L1 {

    public static void main(String[] args){
        int[] aaaa = {1,2,3333,1111,2222};
        int target = 3333;
        System.out.println(JSON.toJSONString(twoSum(aaaa,target)));
    }

    public static int[] twoSum(int[] nums, int target) {
        int[] result = new int[2];
        for (int i = 0; i < nums.length; i++) {
            int a = nums[i];
            for (int j = i+1; j < nums.length; j++) {
                int b = nums[j];
                if (a+b==target){
                    result[0]=i;
                    result[1] = j;
                }
            }
        }
        return result;

    }
}
