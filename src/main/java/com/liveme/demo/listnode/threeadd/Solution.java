package com.liveme.demo.listnode.threeadd;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Solution {


    public static void main(String[] args) {


        int[] nums = new int[]{-1,0,1,2,-1,-4};

        System.out.println(threeAdd(nums));
    }




    /**
     *
     * 思路
     * 1,标签：数组遍历
     * 2,首先对数组进行排序，排序后固定一个数 nums[i]nums[i]，再使用左右指针指向 nums[i]nums[i]后面的两端，
     *      数字分别为 nums[L]nums[L] 和 nums[R]nums[R]，计算三个数的和 sumsum 判断是否满足为 00，
     *      满足则添加进结果集
     * 3,如果 nums[i]nums[i]大于 00，则三数之和必然无法等于 00，结束循环
     * 4,如果 nums[i]nums[i] == nums[i-1]nums[i−1]，则说明该数字重复，会导致结果重复，所以应该跳过
     * 5,当 sumsum == 00 时，nums[L]nums[L] == nums[L+1]nums[L+1] 则会导致结果重复，应该跳过，L++L++
     * 6,当 sumsum == 00 时，nums[R]nums[R] == nums[R-1]nums[R−1] 则会导致结果重复，应该跳过，R--R−−
     * 7,时间复杂度：O(n^2)O(n
     * 2
     *  )，nn 为数组长度
     *
     * */

    public static List<List<Integer>> threeAdd(int[] nums){

        int len = nums.length;
        List<List<Integer>> res = new ArrayList();
        if(nums == null || len < 3) return res;
        Arrays.sort(nums); // 排序
        for (int i = 0; i < nums.length; i++) {

            /**step 1*/
            if (nums[i] >0) {
                break;
            }
            /**step2 去重*/
            if (i >0 && nums[i] == nums[i-1]) {
                continue;
            }
            int L =i+1;
            int R = len-1;
            while (L < R) {
                int sum = nums[i]+nums[L]+nums[R];
                if (sum == 0) {
                    res.add(Arrays.asList(nums[i],nums[L],nums[R]));
                    while (L < R && nums[L] == nums[L+1]) L++;
                    while (L < R && nums[R] == nums[R-1]) R--;
                    L++;
                    R--;
                }

                if (sum >0) R--;
                if (sum <0) L++;
            }
        }

        return res;

    }





}
