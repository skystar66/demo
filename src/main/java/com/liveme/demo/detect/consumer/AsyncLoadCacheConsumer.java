package com.liveme.demo.detect.consumer;

import com.liveme.demo.detect.monitor.ThreadPoolMonitor;
import com.liveme.demo.detect.provider.MQProvider;
import com.liveme.demo.detect.vo.AsyncLoadPageTaskVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.*;

/**
 * @date:2021-03-26
 * @desc:异步执行分页
 */
//@Slf4j
public class AsyncLoadCacheConsumer {

    private static Logger logger = LoggerFactory.getLogger(AsyncLoadCacheConsumer.class);

    private static ThreadPoolExecutor executorAsyncLoadCachePool =
            new ThreadPoolMonitor(500,
                    1000,
                    0L,
                    TimeUnit.MILLISECONDS,
                    new LinkedBlockingQueue<>(10 * 10000), "pool-asyncLoadCache-thread");

    private static ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(5);

    private static class InstanceHolder {
        public static final AsyncLoadCacheConsumer instance = new AsyncLoadCacheConsumer();
    }

    public static AsyncLoadCacheConsumer getInstance() {
        return InstanceHolder.instance;
    }

    public AsyncLoadCacheConsumer() {
    }


    public void start() {

        ExecutorService detectThreadPool = Executors.newFixedThreadPool(MQProvider.threadCnt);
        for (int i = 0; i < MQProvider.threadCnt; i++) {
            detectThreadPool.execute(new ConsumerDetect(
                    i % MQProvider.threadCnt));
        }
        logger.info("AsyncLoadCacheConsumer Detect Async Page Queue Start！！Thread Count:{}", MQProvider.threadCnt);
    }


    public class ConsumerDetect implements Runnable {

        private LinkedBlockingQueue<AsyncLoadPageTaskVO> msgQueue;

        /**
         * @param index 监控对列编号索引
         */
        public ConsumerDetect(int index) {
            this.msgQueue = MQProvider.getFromRPCRoomMsgQueueByIndex(index);
        }

        private void takeQueue() {
            try {
                final int[] count = {0};
                while (true) {
                    AsyncLoadPageTaskVO asyncLoadPageTaskVO = msgQueue.take();
                    count[0]++;
                    if (count[0] > 5) {
                        count[0] = 0;
                    }
                    executorAsyncLoadCachePool.execute(new Runnable() {
                        @Override
                        public void run() {
                            try {

                                System.out.println("执行业务1");

                            } catch (Exception ex) {
//                                log.error("takeQueue Async Page 发生异常：{}", ex);

                            }
                        }
                    });
//                    int finalCount = count[0];
                    scheduledExecutorService.scheduleAtFixedRate(new Runnable() {
                        @Override
                        public void run() {
                            System.out.println("执行业务2:");
                        }
                    }, 0, 2, TimeUnit.SECONDS);
                }
            } catch (Exception ex) {
//                log.error("error:{}", ex);
                try {
                    Thread.sleep(5);
                } catch (InterruptedException e) {
                }
            }
        }

        @Override
        public void run() {
            takeQueue();
        }
    }
}
