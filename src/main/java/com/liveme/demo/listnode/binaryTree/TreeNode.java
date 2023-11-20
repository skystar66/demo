package com.liveme.demo.listnode.binaryTree;

/**
 * @author xuliang
 * @version 1.0
 * @project demo
 * @description
 * @date 2023/11/14 09:49:09
 */
public class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;

    TreeNode() {
    }

    TreeNode(int val) {
        this.val = val;
    }

    TreeNode(int val, TreeNode left, TreeNode right) {
        this.val = val;
        this.left = left;
        this.right = right;
    }
}
