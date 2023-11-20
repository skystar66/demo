package com.liveme.demo.listnode.hasCycle;


/**
 * 链表操作 校验链表是否有环
 */
public class Solution {


    public static void main(String[] args) {
        /**创建链表*/
        ListNode node = createNode();
        /**快慢指针算法*/
        System.out.println(hasCycle(node));
        /**创建环形链表*/
        CircularLinkedList circularLinkedList = createCircularLinkedList();
        /**快慢指针算法校验是否是环形链表*/
        System.out.println(hasCycle(circularLinkedList));
    }


    /**
     * 校验链表是否有环
     */
    public static boolean hasCycle(ListNode head) {


        ListNode fast = head.next;
        ListNode slow = head;

        while (fast != slow) {
            if (fast == null || fast.next == null ||
                    slow == null ||
                    slow.next == null) {
                return false;
            }
            slow = slow.next;
            fast = fast.next.next;
        }
        return true;


    }


    /**
     * 校验链表是否有环
     */
    public static boolean hasCycle(CircularLinkedList head) {


        Node fast = head.getHead().next;
        Node slow = head.getHead();

        while (fast != slow) {
            if (fast == null || fast.next == null ||
                    slow == null ||
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


    public static CircularLinkedList createCircularLinkedList() {
        CircularLinkedList circularLinkedList = new CircularLinkedList();
        circularLinkedList.insert(1);
        circularLinkedList.insert(2);
        circularLinkedList.insert(3);
        circularLinkedList.insert(4);
        circularLinkedList.insert(5);
        circularLinkedList.display();
        return circularLinkedList;
    }


}
