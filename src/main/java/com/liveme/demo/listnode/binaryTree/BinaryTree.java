package com.liveme.demo.listnode.binaryTree;

/**
 * @author xuliang
 * @version 1.0
 * @project demo
 * @description
 * @date 2023/11/14 09:35:42
 */
public class BinaryTree {



    public TreeNode invertTree(TreeNode root) {
        if(root==null){return null;}
        rollTree(root);
        return root;
    }

    /**二叉树深度轮训翻转*/
    public void rollTree(TreeNode root){
        if(root==null){return;}
        TreeNode t=root.left;
        root.left=root.right;
        root.right=t;
        rollTree(root.left);
        rollTree(root.right);
    }

}
