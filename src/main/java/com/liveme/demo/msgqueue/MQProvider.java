package com.liveme.demo.msgqueue;


import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;

public final class MQProvider {

    public static final int threadCnt = 5;//队列数量


    private static final Map<Integer, LinkedBlockingQueue<String>> rPCRoomMsgQueueMap = new HashMap<>();


    static {
        for (int i = 0; i < threadCnt; i++) {
            rPCRoomMsgQueueMap.put(i, new LinkedBlockingQueue<>(3000));
        }
    }

    /**
     * 得到与index相匹配的队列
     *
     * @param index
     * @return
     */
    public static LinkedBlockingQueue<String> getFromRPCRoomMsgQueueByIndex(int index) {
        return rPCRoomMsgQueueMap.get(index);
    }

    /**
     * 得到与key 取模的队列
     *
     * @param key
     * @return
     */
    public static LinkedBlockingQueue<String> getFromRPCRoomMsgQueueByKey(int key) {
        int index = key % threadCnt;
        return rPCRoomMsgQueueMap.get(index);
    }

    /**
     * 得到随机的队列
     *
     * @return
     */
    public static LinkedBlockingQueue<String>  getFromRPCRoomMsgQueueByRandom() {
        return rPCRoomMsgQueueMap.get(RandomUtils.nextInt(0, threadCnt));
    }

    /**
     * push 消息
     *
     * @param msg
     */
    public static void push(String msg) {
        if (StringUtils.isNotEmpty(msg)) {
            getFromRPCRoomMsgQueueByRandom()
                    .offer(msg);
        }
    }


    /**
     * push 消息
     *
     * @param msg
     */
    public static void push(String msg,String roomId) {

        if (StringUtils.isNotEmpty(roomId)) {
            getFromRPCRoomMsgQueueByKey(roomId.hashCode()).offer(msg);
            return;
        }
        if (StringUtils.isNotEmpty(msg)) {
            getFromRPCRoomMsgQueueByRandom()
                    .offer(msg);
        }
    }
}
