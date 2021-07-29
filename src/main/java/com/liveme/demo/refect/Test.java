package com.liveme.demo.refect;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class Test {


    public static void main(String[] args) {


        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(1, 2, 0, TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(2));


        for (int i = 0; i < 40; i++) {
            threadPoolExecutor.execute(new Runnable() {
                @Override
                public void run() {
                    try {

                        System.out.println("1111");
                    } catch (Exception ex) {
                        System.out.println("come in ex");
                    }
                }
            });

        }


    }


}
