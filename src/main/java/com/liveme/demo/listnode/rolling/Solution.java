package com.liveme.demo.listnode.rolling;


import java.util.List;

/**翻转链表*/
public class Solution {


    public static ListNode reverseKGroup(ListNode head, int k) {
        ListNode dummy = new ListNode(0);
        dummy.next = head;

        ListNode pre = dummy;
        ListNode end = dummy;


        while (end.next != null) {
            for (int i = 0; i < k && end != null; i++){
                end = end.next;
            }
            if (end == null) break;
            //记录下要翻转链表的头节点
            ListNode start = pre.next;
            //下次要翻转的
            ListNode next = end.next;
            //断开链表
            end.next = null;
            //翻转链表,pre.next指向翻转后的链表。1->2 变成2->1。 dummy->2->1
            pre.next = reverse(start);
            //翻转后头节点变到最后。通过.next把断开的链表重新链接。
            start.next = next;
            //将pre换成下次要翻转的链表的头结点的上一个节点。即start
            pre = start;
            //翻转结束，将end置为下次要翻转的链表的头结点的上一个节点。即start
            end = pre;
        }
        return dummy.next;
    }

    private static ListNode reverse(ListNode head) {

        //前一个节点指针
        ListNode pre = null;
        //当前节点指针
        ListNode curr = head;
        while (curr != null) {
            ListNode next = curr.next;
            curr.next = pre;
            pre = curr;
            curr = next;
        }
        return pre;
    }


    public static void main(String[] args) {
        /**创建链表*/
        ListNode node = createNode();

        reverseKGroup(node, 2).print();
    }


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
