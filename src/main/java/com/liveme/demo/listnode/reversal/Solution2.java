package com.liveme.demo.listnode.reversal;

/**
 * 反转链表 给定 m n 1 ≤ m ≤ n ≤ 链表长度。
 */
public class Solution2 {

    public static void main(String[] args) {


        ListNode head = createNode();


        reverseBetween(head,2,4).print();
    }

    public static ListNode reverseBetween(ListNode head, int m, int n) {

        if (m<1 || n<1 || m>=n) {
            return head;
        }
        ListNode dummNode = new ListNode(0,head);
        ListNode pre = dummNode;
        ListNode fast = dummNode;
        ListNode slow = dummNode;
        for (int i=0;i<n;i++) {
            fast = fast.next;
        }
        ListNode temp = fast.next;
        //先让slow 先走 m步
        for (int i=1;i<m;i++) {
            slow=slow.next;
        }
        ListNode sloNode = slow.next;
        //断开链表
        fast.next=null;
        ListNode reversalNode =reversalNode(sloNode);
        slow.next=reversalNode;
        sloNode.next=temp;
        return pre.next;
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
