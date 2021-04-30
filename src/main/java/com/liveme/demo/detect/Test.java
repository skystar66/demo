package com.liveme.demo.detect;

import com.liveme.demo.detect.consumer.AsyncLoadCacheConsumer;
import com.liveme.demo.detect.provider.MQProvider;
import com.liveme.demo.detect.vo.AsyncLoadPageTaskVO;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

public class Test {


    public static void main(String[] args) {

//        AsyncLoadCacheConsumer.getInstance().start();
//
//
//
//        for (int i=0;i<1000;i++) {
//            AsyncLoadPageTaskVO asyncLoadPageTaskVO = new AsyncLoadPageTaskVO();
//            MQProvider.push(asyncLoadPageTaskVO);
//
//        }
//
//
        LinkedBlockingQueue<String> linkedBlockingQueue = new LinkedBlockingQueue<>();

        linkedBlockingQueue.add("1");
        linkedBlockingQueue.add("2");
        linkedBlockingQueue.add("3");
        linkedBlockingQueue.add("4");
        linkedBlockingQueue.add("5");


        List<String> lists = new ArrayList<>();
        linkedBlockingQueue.drainTo(lists,10);

        for (String list : lists) {
            System.out.println(list);
        }





    }


}
