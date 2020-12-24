//package com.liveme.demo.msgqueue;
//
//import com.liveme.demo.msgqueue.result.StaticMessageRecord;
//import com.liveme.demo.util.Constants;
//import org.springframework.util.CollectionUtils;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.concurrent.ConcurrentLinkedQueue;
//
//public class MQConsumer implements Runnable {
//
//
//    private ConcurrentLinkedQueue<String> msgQueue;
//
//    //queue 的序号
//    private int queueNum;
//
//    private int threadSeqNum;
//
//    public MQConsumer(int queueNum,int threadSeqNum) {
//        this.msgQueue =  MQProvider.getFromRPCRoomMsgQueueByIndex(queueNum);
//        this.queueNum = queueNum;
//        this.threadSeqNum=threadSeqNum;
////        System.out.println("当前队列数量:"+msgQueue.size()+"个");
//    }
//    public MQConsumer(ConcurrentLinkedQueue<String> msgQueue) {
//        this.msgQueue =  msgQueue;
//
//    }
//
//
//    @Override
//    public void run() {
//        String poll = null;
////        List<String> tempArray = new ArrayList<>();
//        while ((poll = msgQueue.poll()) != null ){
//
//        }
//    }
//}
