package com.liveme.demo.weight;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class test {


    public static void main(String[] args) {

        ActionNodeContext actionNodeContext = new ActionNodeContext();

        NodeInfo nodeInfo = new NodeInfo(20, "node1");
        NodeInfo nodeInfo1 = new NodeInfo(10, "node2");
        NodeInfo nodeInfo2 = new NodeInfo(20, "node3");
        NodeInfo nodeInfo3 = new NodeInfo(40, "node4");
        NodeInfo nodeInfo4 = new NodeInfo(10, "node5");
        actionNodeContext.addNode(nodeInfo);
        actionNodeContext.addNode(nodeInfo1);
        actionNodeContext.addNode(nodeInfo2);
        actionNodeContext.addNode(nodeInfo3);
        actionNodeContext.addNode(nodeInfo4);
        actionNodeContext.initWeight();

        Map<String, Integer> map = new HashMap<>();
        for (int i = 0; i < 100000; i++) {
            NodeInfo nodeInfos = actionNodeContext.nextNode();
            if (map.containsKey(nodeInfos.getName())) {
                map.put(nodeInfos.getName(), map.get(nodeInfos.getName()) + 1);
            } else {
                map.put(nodeInfos.getName(), 1);

            }
        }

        for (Map.Entry<String, Integer> stringIntegerEntry : map.entrySet()) {
            System.out.println(stringIntegerEntry.getKey() + "----" + stringIntegerEntry.getValue());
        }


    }


}
