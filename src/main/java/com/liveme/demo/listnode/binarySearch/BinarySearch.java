package com.liveme.demo.listnode.binarySearch;

/**
 * @author xuliang
 * @version 1.0
 * @project 二分查找，利用中位数
 * @description
 * @date 2023/11/14 10:01:05
 */
public class BinarySearch {

    public static int binarySearch(int[] nums, int target){
        int low = 0;
        int high = nums.length -1;
        int mid;
        while(low <= high){
            mid = (high + low) / 2;
            if(nums[mid] == target){
                return mid;
            }else if(nums[mid] < target){
                low = mid + 1;
            }else{
                high = mid - 1;
            }
        }
        return -1;
    }


    public static void main(String[] args) {


        int[] nums=new int[]{1,2,3,4,5,6,7,8};
        System.out.println(binarySearch(nums,2));


    }


}
