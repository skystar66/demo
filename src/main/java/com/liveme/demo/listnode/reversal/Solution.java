package com.liveme.demo.listnode.reversal;

/**
 * 反转链表
 */
public class Solution {

    public static void main(String[] args) {


        ListNode head = createNode();
        reversalNode(head).print();
    }

    /**
     * 旋转链表，快慢指针
     */
    public static ListNode reversalNode(ListNode head) {

        ListNode pre = null;
        ListNode cur = head;
        while (cur!=null) {
            ListNode next = cur.next;//新增临时节点
            cur.next=pre;//将pre 指向curr.next 的节点
            pre=cur;//pre 暂存cur
            cur=next;//cur 下一个访问的节点
        }

        return pre;

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
