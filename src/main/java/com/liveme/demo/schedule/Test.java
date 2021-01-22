package com.liveme.demo.schedule;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Test {


    /**
     * 处理定时任务线程池
     */
    private static final ScheduledExecutorService SCHEDULED_EXECUTOR_SERVICE =
            Executors.newScheduledThreadPool(2);//处理初始化数据源,2个线程


    public static void main(String[] args) {

//        SCHEDULED_EXECUTOR_SERVICE.scheduleAtFixedRate(new Runnable() {
//            @Override
//            public void run() {
//                System.out.println("123456789");
//            }
//        },0,3, TimeUnit.SECONDS);


        String uid = "11111";

        String[] toIds = uid.split(",");

        System.out.println(toIds);

    }



}
