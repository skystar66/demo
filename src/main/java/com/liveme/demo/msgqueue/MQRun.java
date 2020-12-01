package com.liveme.demo.msgqueue;

import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 * mq 处理任务执行类
 */
public class MQRun {

    //线程池max/core thread 数量
    private static int threadPoolCount = 50;

    private static ExecutorService threadPool =
            Executors.newFixedThreadPool(threadPoolCount);
    //处理每个队列的线程数
    private static int threadQueueCount = 5;

    public static void main(String[] args) {
        int queueCount = MQProvider.threadCnt;
        //生产消息 10000条消息
        for (int i = 0; i < 500; i++) {
            String roomId = UUID.randomUUID().toString().replace("-", "");
            MQProvider.push(roomId, roomId);
        }
        //50个线程消费消息
        for (int i = 0; i < threadPoolCount; i++) {
            threadPool.submit(new MQConsumer(
                    i % queueCount,
                    i + 1));
        }
    }
}
