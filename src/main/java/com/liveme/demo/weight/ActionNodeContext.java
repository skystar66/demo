package com.liveme.demo.weight;

import java.util.ArrayList;
import java.util.List;

public class ActionNodeContext {
    private final List<NodeInfo> nodeInfos = new ArrayList<>();

    private short[] indexMap;//下标为weight,值为nodeInfos对应的节点index,提升选择性能
    private volatile int weightIndex = -1;
    private int weightSum;

    public void addNode(NodeInfo nodeInfo) {
        if (!nodeInfos.contains(nodeInfo)) {
            nodeInfos.add(nodeInfo);
        }
    }

    public void removeNode(NodeInfo nodeInfo) {
        nodeInfos.remove(nodeInfo);
    }

    //根据权重获取节点
    public NodeInfo nextNode() {
        if (nodeInfos.size() == 1) return nodeInfos.get(0);
        if (nodeInfos.size() == 0) return null;
        return nodeInfos.get(indexMap[nextIndex()]);
    }

    //加锁,并发有安全问题
    public synchronized int nextIndex() {
        weightIndex++;
        if (weightIndex >= weightSum) weightIndex = 0;
        return weightIndex;
    }

    //刷新权重映射
    public void initWeight() {
        weightSum = 0;
        for (NodeInfo nodeInfo : nodeInfos) weightSum += nodeInfo.getWeight();
        //权重值总和
        indexMap = new short[weightSum];
        //索引
        short index = 0;
        //偏移量
        int offset = 0;
        for (NodeInfo nodeInfo : nodeInfos) {
            for (int i = 0; i < nodeInfo.getWeight(); i++) {
                indexMap[i + offset] = index;
            }
            offset += nodeInfo.getWeight();
            index++;
        }
    }
}
