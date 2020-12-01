package com.liveme.demo.msgqueue;

import java.util.concurrent.LinkedBlockingQueue;

public class MQConsumer implements Runnable {


    private LinkedBlockingQueue<String> msgQueue;

    //queue 的序号
    private int queueNum;

    private int threadSeqNum;

    public MQConsumer(int queueNum,int threadSeqNum) {
        this.msgQueue =  MQProvider.getFromRPCRoomMsgQueueByIndex(queueNum);
        this.queueNum = queueNum;
        this.threadSeqNum=threadSeqNum;
    }


    @Override
    public void run() {

        while (true) {
            if (!msgQueue.isEmpty()) {
                try {
                    String msg = msgQueue.take();
                    Thread.sleep(100);
                    System.out.println("threadName : "+Thread.currentThread().getName()+"  " +
                            "consumer queue msg:" + msg + ",queueSeqNum :"
                            + queueNum +",threadSeqNum:"+threadSeqNum);
                } catch (InterruptedException ignore) {
                }
            }
        }
    }
}
