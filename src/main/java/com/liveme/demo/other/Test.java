package com.liveme.demo.other;

import java.util.concurrent.*;

public class Test {


    public static void main(String[] args) {
        System.out.println(getEndMoney());
        System.out.println(getEndMoney2());


//        System.out.println(print());


        ConcurrentMap<String, ConcurrentHashMap<String, String>> gagMappingMap = new ConcurrentHashMap<String, ConcurrentHashMap<String, String>>();
        ConcurrentHashMap<String, String> temp = new ConcurrentHashMap<>();
        temp.put("a","1");
        temp.put("b","1");
        temp.put("c","1");

        gagMappingMap.put("1",temp);

        System.out.println(gagMappingMap.get("1").size());

        temp.remove("a");
        temp.remove("b");
        temp.remove("c");


        System.out.println(gagMappingMap.get("1"));



        print();
        print2();

//        System.out.println("hello word!!!");

//
//
//        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(30,
//                new ThreadFactory() {
//            private int num = 0;
//            @Override
//            public Thread newThread(Runnable r) {
//                Thread thread = new Thread(r);
//                num ++;
//                thread.setName("room-message-schedule" + num);
//                return thread;
//            }
//        });
//
//        //每200ms 遍历一次队列
//        scheduledExecutorService.scheduleAtFixedRate(new Runnable() {
//            @Override
//            public void run() {
//                System.out.println(Thread.currentThread().getName()+"-----111");
//            }
//        },0,5000, TimeUnit.MILLISECONDS);
//


    }


    public static void print(){

        System.out.println("我是学生1");

    }


    public static void print2(){
        System.out.println("我是学生2");
    }












    public static double getEndMoney(){

        double day = 21.75;
        double hour = 8;//每天时长
        double month = 12;//月数
        //总时长
        double sumHour = day*hour*month;// 全年总工作时长
        double workerDay = day*4;//已工作天数
        double workerHour = hour*workerDay;//已工作时长
        return workerHour/sumHour*38000*2.5;//工作时长/全年总时长*月薪*系数



    }

    public static double getEndMoney2(){


        LinkedBlockingQueue linkedBlockingQueue = new LinkedBlockingQueue();
//        linkedBlockingQueue

        double avgMonth = 38000/12;

        return avgMonth*4*2.5;


    }


}
