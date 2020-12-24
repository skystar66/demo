package com.liveme.demo.msgqueue.result;


import java.util.concurrent.atomic.AtomicLong;

public class StaticMessageRecord {

    public static AtomicLong atomicLong = new AtomicLong(0);

////
//    static {
//
//        init();
//    }


    public static void init(){


        while (true) {


            try {

                System.out.println(atomicLong.get()+"条消息");
                Thread.sleep(1000);
            }catch (Exception ex) {

            }


        }

    }

}
