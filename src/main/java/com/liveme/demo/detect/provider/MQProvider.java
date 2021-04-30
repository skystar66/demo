package com.liveme.demo.detect.provider;


import com.liveme.demo.detect.vo.AsyncLoadPageTaskVO;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.LinkedBlockingQueue;

public final class MQProvider {

    public static int threadCnt = Runtime.getRuntime().availableProcessors() * 2;
    public static final Map<Integer, LinkedBlockingQueue<AsyncLoadPageTaskVO>>
        rPCRoomMsgQueueMap = new HashMap<>();
    private static Random random = new Random();

    static {
        for (int i = 0; i < threadCnt; i++) {
            rPCRoomMsgQueueMap.put(i,
                new LinkedBlockingQueue<>());
        }
    }

    /**
     * 得到与index相匹配的队列
     *
     * @param index
     * @return
     */
    public static LinkedBlockingQueue<AsyncLoadPageTaskVO> getFromRPCRoomMsgQueueByIndex(int index) {
        return rPCRoomMsgQueueMap.get(index);
    }

    /**
     * 得到与key 取模的队列
     *
     * @param key
     * @return
     */
    public static LinkedBlockingQueue<AsyncLoadPageTaskVO> getFromRPCRoomMsgQueueByKey(int key) {
        int index = key % threadCnt;
        return rPCRoomMsgQueueMap.get(index);
    }

    /**
     * 得到随机的队列
     *
     * @return
     */
    public static LinkedBlockingQueue<AsyncLoadPageTaskVO> getFromRPCRoomMsgQueueByRandom() {
        return rPCRoomMsgQueueMap.get(random.nextInt(threadCnt));
    }

    /**
     * push 消息
     *
     * @param msg
     */
    public static void push(AsyncLoadPageTaskVO msg) {
        if (null != msg) {
            getFromRPCRoomMsgQueueByRandom()
                .offer(msg);
        }
    }


//    /**
//     * push 消息
//     *
//     * @param msg
//     */
//    public static void push(LiveRoom msg,String roomId) {
//
//        if (StringUtils.isNotEmpty(roomId)) {
//            try {
//                getFromRPCRoomMsgQueueByKey(roomId.hashCode()).offer(msg);
//            }catch (Exception ex) {
//                ex.printStackTrace();
//                System.out.println(roomId+"error"+ex);
//            }
//            return;
//        }
//        if (null != msg) {
//            getFromRPCRoomMsgQueueByRandom()
//                    .offer(msg);
//        }
//    }


    public static void main(String[] args) {
        getFromRPCRoomMsgQueueByKey("1000".hashCode());
    }

}
