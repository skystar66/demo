package com.liveme.demo.listnode.binaryTree;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * @author xuliang
 * @version 1.0
 * @project demo
 * @description
 * @date 2023/11/14 10:52:32
 */
public class BinaryTreeScan {


    public static void main(String[] args) {

//        System.out.println(levelOrder());
    }

    /**
     * 遍历二叉树
     */
    public static List<List<Integer>> levelOrder(TreeNode root) {
        List<List<Integer>> ret = new ArrayList<>();
        // 首先判断根节点非空
        if (root == null) {
            return ret;
        }
        // 定义队列
        Queue<TreeNode> queue = new LinkedList<>();
        // 初始根节点入队
        queue.offer(root);
        // 队列非空循环
        while (!queue.isEmpty()) {
            // 用于存储该层节点数值
            List<Integer> level = new ArrayList<Integer>();
            // 每轮出队该层的全部节点，提前记录该层节点个数，队列会变
            int currentLevelSize = queue.size();
            for (int i = 1; i <= currentLevelSize; ++i) {
                TreeNode node = queue.poll();
                level.add(node.val);
                // 如果左右孩子非空则入队
                if (node.left != null) {
                    queue.offer(node.left);
                }
                if (node.right != null) {
                    queue.offer(node.right);
                }
            }
            // 添加该层节点数值列表到结果
            ret.add(level);
        }

        return ret;

    }


}
