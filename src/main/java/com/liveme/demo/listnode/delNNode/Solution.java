package com.liveme.demo.listnode.delNNode;

/**
 * 链表操作 删除链表的倒数第N个节点
 */
public class Solution {


    public static void main(String[] args) {
        /**创建链表*/
        ListNode node = createNode();

        /**快慢指针算法*/
        removeNthFromEnd2(node, 2).print();

    }


    /**
     * 方法2，快慢指针
     * 我们可以使用两个指针 first 和 second 同时对链表进行遍历，
     * 并且 first 比 second 始终超前 n 个节点，
     * 当 first 遍历到链表的末尾时，second 就恰好处于倒数第 n+1 个节点
     * 所以 second 的下一个节点就是我们需要删除的节点。
     */
    public static ListNode removeNthFromEnd2(ListNode head, int n) {

        //创建哑结点
        //两个指针
        ListNode first = head;
        ListNode sencond = head;

        //先让 first 先走N 步
        for (int i = 0; i < n; i++) {
            first = first.next;
        }
        //快慢指针一起走,当first 的next 为null 时，表示链表已全部走完
        while (first.next != null) {
            first = first.next;
            sencond = sencond.next;
        }

        //将慢指针的next节点删除
        sencond.next = sencond.next.next;
        return head;

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
