package com.liveme.demo.listnode.happy;

public class Solution {


    public static void main(String[] args) {


        System.out.println(isHappy(19));


    }


    /**
     * 计算一个数的平方和 例如：19 = 1的平方 + 9的平方
     */
    public static int getNext(int n) {
        int totalSum = 0;
        while (n > 0) {
            int d = n % 10;
            n = n / 10;
            totalSum += d * d;
        }
        return totalSum;

    }

    /**
     * 快慢指针方法,校验是否是快乐数
     */
    public static boolean isHappy(int n) {

        int slow = n;
        int fast = getNext(n);

        /**当fast等于1的时候，表示是快乐数，当fast 与 slow、 相遇的时候，表示不是快乐数，又回到了原点*/
        while (fast != 1 && fast != slow) {
            slow = getNext(slow);
            /**fast比slow 快走2步*/
            fast = getNext(getNext(fast));
        }
        return fast == 1;
    }


}
