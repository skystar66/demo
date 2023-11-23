package com.liveme.demo.listnode.arrayReversal;

/**
 * @author xuliang
 * @version 1.0
 * @project 数组翻转
 * @description
 * @date 2023/11/14 11:06:42
 */
public class ArrayReversal {
    public static void reverseArray(int[] array) {
        int start = 0;
        int end = array.length - 1;

        while (start < end) {
            // 交换数组的两个元素
            int temp = array[start];
            array[start] = array[end];
            array[end] = temp;

            start++;
            end--;
        }
    }

    public static void main(String[] args) {
        int[] array = {1, 2, 3, 4, 5};

        System.out.println("原始数组：");
        for (int num : array) {
            System.out.print(num + " ");
        }

        reverseArray(array);

        System.out.println("\n反转后的数组：");
        for (int num : array) {
            System.out.print(num + " ");
        }
    }
}
