package com.liveme.demo.queue;

import org.apache.lucene.util.PriorityQueue;


public class PriotiryMsgQueue extends PriorityQueue<QueueMsg> {


    public PriotiryMsgQueue(int maxSize) {
        super(maxSize);
    }


    /**
     * @Description: 消息入队
     * @Param: [message]
     * @return: void
     * @Author: xl
     * @Date: 2021/6/5
     **/
    public void addMessage(QueueMsg message) {
        insertWithOverflow(message);
    }



    @Override
    protected boolean lessThan(QueueMsg asyncLoadPageTaskVO, QueueMsg t1) {
        return asyncLoadPageTaskVO.getOrder() > t1.getOrder();
    }
}
