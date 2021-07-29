package com.liveme.demo.queue;

public class Test {


    public static void main(String[] args) {

        PriotiryMsgQueue priotiryMsgQueue = new PriotiryMsgQueue(4);

        QueueMsg queueMsg = new QueueMsg();
        queueMsg.setMsg("msg1");
        queueMsg.setOrder(4);

        priotiryMsgQueue.addMessage(queueMsg);


        QueueMsg queueMsg2 = new QueueMsg();
        queueMsg2.setMsg("msg1");
        queueMsg2.setOrder(3);
        priotiryMsgQueue.addMessage(queueMsg2);



        QueueMsg queueMsg3 = new QueueMsg();
        queueMsg3.setMsg("msg1");
        queueMsg3.setOrder(10);
        priotiryMsgQueue.addMessage(queueMsg3);


        QueueMsg queueMsg4 = new QueueMsg();
        queueMsg4.setMsg("msg1");
        queueMsg4.setOrder(7);
        priotiryMsgQueue.addMessage(queueMsg4);


        QueueMsg msg = null;

        while ((msg=priotiryMsgQueue.pop()) != null) {

            System.out.println(msg.getOrder());
        }


    }


}
