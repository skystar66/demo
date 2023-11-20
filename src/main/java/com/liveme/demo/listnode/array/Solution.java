package com.liveme.demo.listnode.array;

public class Solution {


    public static void main(String[] args) {


        int[] nums = new int[]{1, 1, 2, 3, 4, 4};

        System.out.println(removeDuplicates(nums));
        System.out.println(nums);
    }


    public static int removeDuplicates(int[] nums) {

        if (nums == null || nums.length < 1) {
            return 0;
        }

        int count = 0;
        for (int i = 1; i < nums.length; i++) {

            if (nums[i] != nums[count]) {
                count++;
                nums[count] = nums[i];


            }


        }


        return count + 1;




    }


}
