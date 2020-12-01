package com.liveme.demo.listnode.rotate;

/**
 * 旋转链表
 */
public class Solution {

    public static void main(String[] args) {


        ListNode head = createNode();

        rotateRight(head, 2).print();
    }

    /**
     * 旋转链表，快慢指针
     */
    public static ListNode rotateRight(ListNode head, int k) {

        //声明快指针 慢指针
        ListNode fast = head;
        ListNode slow = head;

        //链表的长度
        int length = 1;

        //统计链表的长度，顺便找到链表的尾结点
        while (fast.next != null) {
            fast = fast.next;
            length++;
        }
        //首尾相连 构成环
        fast.next = head;
        //计算慢指针移动的步数
        int step = length - k % length;
        //移动步数，这里大于1实际上是少移了一步 ，主要是断开节点，变成尾结点
        while (step-- > 1) {
            slow = slow.next;
        }
        //temp就是需要返回的结点
        ListNode temp = slow.next;
        //因为链表是环形的，slow就相当于尾结点了，
        //直接让他的next等于空
        slow.next = null;
        return temp;

    }


    /**
     * 创建链表
     */
    public static ListNode createNode() {

        ListNode l1 = new ListNode(1);    //创建链表对象 l1 （对应有参 和 无参 构造方法）
        l1.add(2);                //插入结点，打印
        l1.add(3);
        l1.add(4);
        l1.add(5);
        l1.print();

        System.out.println(l1.toString());
        return l1;
    }


}
