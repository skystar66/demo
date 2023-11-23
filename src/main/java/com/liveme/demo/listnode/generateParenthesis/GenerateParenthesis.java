package com.liveme.demo.listnode.generateParenthesis;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @author xuliang
 * @version 1.0
 * @project demo
 * @description
 * @date 2023/11/17 09:39:31
 */
public class GenerateParenthesis {

    static List<String> res = new ArrayList<>();
    public static void main(String[] args) {
        int[] nums=new int[]{2,3,6,7};
        System.out.println(combinationSum(nums,7));
        System.out.println(generateParenthesis(3));
    }

    public static List<String> generateParenthesis(int n) {
        dfs(n, n, "");
        return res;
    }

    private static void dfs(int left, int right, String curStr) {
        if (left == 0 && right == 0) { // 左右括号都不剩余了，递归终止
            res.add(curStr);
            return;
        }
        if (left > 0) { // 如果左括号还剩余的话，可以拼接左括号
            dfs(left - 1, right, curStr + "(");
        }
        if (right > left) { // 如果右括号剩余多于左括号剩余的话，可以拼接右括号
            dfs(left, right - 1, curStr + ")");
        }
    }

    static List<List<Integer>> res1 = new ArrayList();
    public static List<List<Integer>> combinationSum(int[] candidates, int target) {
        dfs(0,candidates,target,new LinkedList());
        return res1;
    }

    static void dfs(int j,int[] arr,int target,LinkedList<Integer> list){
        if(target<0) return;
        if(target==0){
            res1.add(new ArrayList(list));
            return;
        }
        /**2 3 6 7*/
        for(int i=j;i<arr.length;i++){
            list.add(arr[i]);
            dfs(i,arr,target-arr[i],list);
            list.removeLast();
        }
    }

}
