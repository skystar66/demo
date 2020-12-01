package com.liveme.demo.limit.task;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**初始化redis配置到本地缓存*/
@Component
public class InitConfigTask {





    private  static ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(10);


    @PostConstruct
    public void init(){

        //每十分钟加在一次配置
        scheduledExecutorService.schedule(()->{






        },10, TimeUnit.MINUTES);


    }




}
