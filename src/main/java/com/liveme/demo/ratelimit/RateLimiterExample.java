package com.liveme.demo.ratelimit;

import com.google.common.util.concurrent.RateLimiter;

import java.util.Calendar;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

public class RateLimiterExample {




    private static AtomicInteger atomicInteger = new AtomicInteger(10);





    public static void main(String[] args) throws InterruptedException {
        // qps设置为5，代表一秒钟只允许处理五个并发请求
        RateLimiter rateLimiter = RateLimiter.create(50);
//        RateLimiter rateLimiter2 = RateLimiter.create(10);
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        int nTasks = 100;
        long start = System.currentTimeMillis();
        for (int i = 0; i < nTasks; i++) {
            final int j = i;
            executorService.submit(new Runnable() {
                @Override
                public void run() {
                    if (rateLimiter.tryAcquire()) {
                        System.out.println(Thread.currentThread().getName() + " gets job " + j + " done"+"，time:"+ Calendar.getInstance().getTimeInMillis());
                    }
                }
            });


            try {
//                Thread.sleep(1000);
            }catch (Exception ex) {

            }
        } try {
                Thread.sleep(5000);
        }catch (Exception ex) {

        }
        long end = System.currentTimeMillis();
        System.out.println("10 jobs gets done by 5 threads concurrently in " + (end - start) + " milliseconds");
    }
}
