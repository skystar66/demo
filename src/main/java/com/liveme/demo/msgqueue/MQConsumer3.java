package com.liveme.demo.msgqueue;

import com.liveme.demo.msgqueue.room.LiveRoom;
import com.liveme.demo.util.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class MQConsumer3 implements Runnable {

    private static Logger logger = LoggerFactory.getLogger(MQConsumer3.class);

    private static ExecutorService executors = Executors.newFixedThreadPool(MQProvider.threadCnt);


    private ConcurrentLinkedQueue<LiveRoom> msgQueue;


    public MQConsumer3(int queueNum) {
        this.msgQueue = MQProvider.getFromRPCRoomMsgQueueByIndex(queueNum);
        executors.execute(new Runnable() {
            @Override
            public void run() {
                logger.info("当前队列数量:"+msgQueue.size()+"个");

            }
        });
    }

    private void dumpQueue() {
        LiveRoom poll = null;
        int count = 0;
        while ((poll = msgQueue.poll()) != null) {
            poll.batchSend();
        }

    }

    @Override
    public void run() {

        dumpQueue();

    }
}
