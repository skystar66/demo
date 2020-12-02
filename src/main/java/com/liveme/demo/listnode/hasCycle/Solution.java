package com.liveme.demo.listnode.hasCycle;


/**
 * 链表操作 删除链表的倒数第N个节点
 */
public class Solution {


    public static void main(String[] args) {
        /**创建链表*/
        ListNode node = createNode();

        /**快慢指针算法*/
        hasCycle(node);

    }


    /**
     * 校验链表是否有环
     */
    public static boolean hasCycle(ListNode head) {


        ListNode fast = head.next;
        ListNode slow = head;

        while (fast != slow) {
            if (fast == null || fast.next == null ||
                    slow==null ||
                    slow.next == null) {
                return false;
            }
            slow = slow.next;
            fast = fast.next.next;
        }
        return true;


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
