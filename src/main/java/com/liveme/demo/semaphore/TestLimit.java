package com.liveme.demo.semaphore;

import java.util.concurrent.*;

public class TestLimit {


    //每次只允许最多10个线程访问
    static final Semaphore semaphore = new Semaphore(3);


    static ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);

    static ExecutorService executorService = Executors.newCachedThreadPool();

    public static void main(String[] args) {


        scheduledExecutorService.scheduleAtFixedRate(() -> {


        System.out.println("======================================");
        int count = 0;
        for (int i = 0; i < 5; i++) {

            executorService.submit(() -> {

                System.out.println(semaphoreTest());
            });
        }
        }, 0, 10, TimeUnit.SECONDS);


    }

    public  static String semaphoreTest() {
        if (!semaphore.tryAcquire()) {
            return "调用太频繁了，拒绝服务！";
        }
        try {
            Thread.sleep(2000);
            return "ok";
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            semaphore.release();
        }
        return "fail";
    }
}
