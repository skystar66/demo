package com.liveme.demo.listnode.addtwo;


/**
 * 两数相加
 */
public class Solution {


    public static void main(String[] args) {
        /**创建链表1*/
       ListNode l1 = createNode1();
        System.out.println();
       ListNode l2 = createNode2();
        System.out.println();
       addTwoNumbers(l1,l2).print();

    }


    /**
     * 1,找到链表的长度
     * 2,删除从链表开头数起的第(L-N+1)个节点
     * 3，查询链表长度的复杂度：O(n)+删除从链表数起的复杂度：O(n)
     * 4，整体时间复杂度 O(2n)
     */
    public static ListNode addTwoNumbers(ListNode l1, ListNode l2) {


        ListNode pre = new ListNode(0);
        ListNode cur = pre;

        //进位值
        int carry=0;

        while (l1 != null || l2 != null) {

            int x = l1 == null?0:l1.val;
            int y = l2 == null?0:l2.val;

            int sum = x+y+carry;

            //计算进位值
            carry = sum /10;
            //计算当前节点值
            sum=sum%10;
            //生成新的节点
            cur.next = new ListNode(sum);
            //追加到cur 的下一个节点
            cur=cur.next;

            if (l1 != null) {
                l1=l1.next;
            }
            if (l2 != null) {
                l2=l2.next;
            }
        }

        if (carry ==1) {
            cur.next = new ListNode(carry);
        }



        return pre.next;

    }





    public static ListNode createNode1() {

        ListNode l1 = new ListNode(7);    //创建链表对象 l1 （对应有参 和 无参 构造方法）
        l1.add(8);                //插入结点，打印
        l1.add(9);
        l1.print();
        return l1;
    }



    public static ListNode createNode2() {

        ListNode l1 = new ListNode(3);    //创建链表对象 l1 （对应有参 和 无参 构造方法）
        l1.add(2);
        l1.print();
        return l1;
    }



}
