package com.liveme.demo.listnode.dynamic;

import java.util.ArrayDeque;
import java.util.Queue;

/**
 * @author xuliang
 * @version 1.0
 * @project 动态规划路线
 * @description
 * @date 2023/11/14 09:32:37
 */
public class DynamicMap {

    public int minReorder(int n, int[][] connections) {
        boolean[] access = new boolean[n];
        access[0] = true;
        int len = connections.length;
        Queue<int[]> que = new ArrayDeque<>();
        int count = 0;
        for(int i = 0; i < len; i++){
            if(access[connections[i][1]]){
                access[connections[i][0]] = true;
            }else{
                que.add(connections[i]);
            }
        }
        while(!que.isEmpty()){
            int[] item = que.poll();
            if(access[item[1]]){
                access[item[0]] = true;
            }else if(access[item[0]]){
                access[item[1]] = true;
                count++;
            }else{
                que.add(item);
            }
        }
        return count;
    }
}
