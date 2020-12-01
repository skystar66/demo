package com.liveme.demo.limit;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class QpsRecordBucket {



    private static class InstanceHolder {
        public static final QpsRecordBucket instance = new QpsRecordBucket();
    }

    public static QpsRecordBucket getInstance() {
        return QpsRecordBucket.InstanceHolder.instance;
    }



    private static ConcurrentHashMap<String, Long> appIdMap = new ConcurrentHashMap<>();
    private static ConcurrentHashMap<Integer, Long> cMDMap = new ConcurrentHashMap<>();


    /**10s中重置一次计数*/
    private long resetTimeout = 10*1000;

    /**设置cmd 计数*/
    public void cmd(Integer cmd){
        cMDMap.put(cmd,System.currentTimeMillis()+resetTimeout);
    }


    /**设置appId计数*/
    public void appId(String appId){
        appIdMap.put(appId,System.currentTimeMillis()+resetTimeout);

    }




    /**设置cmd 计数*/
    public void cmdReset(Integer cmd){
        cMDMap.remove(cmd);
        QpsRecordCache.getInstance().cmdReset(cmd);
    }


    /**设置appId计数*/
    public void appIdReset(String appId){
        appIdMap.remove(appId);
        QpsRecordCache.getInstance().appIdReset(appId);

    }







    /**
     * 删除过期缓存
     */
    public void delTimeOut() {
        for (Map.Entry<String, Long> stringStringEntry : appIdMap.entrySet()) {
            if (stringStringEntry.getValue() < System.currentTimeMillis()) {
                appIdReset(stringStringEntry.getKey());
            }
        }

        for (Map.Entry<Integer, Long> stringStringEntry : cMDMap.entrySet()) {
            if (stringStringEntry.getValue() < System.currentTimeMillis()) {
                cmd(stringStringEntry.getKey());
            }
        }
    }


    /**
     * 开启清理过期缓存的线程
     */
    public void startCleanThread() {
        ResetQpsTimeOutThread cleanRoomGagTimeOutThread = new ResetQpsTimeOutThread();
        Thread thread = new Thread(cleanRoomGagTimeOutThread);
        //设置为后台守护线程
        thread.setDaemon(true);
        thread.setName("resetQpsTimeoutThread");
        thread.start();
    }


    class ResetQpsTimeOutThread implements Runnable {
        @Override
        public void run() {
            while (true) {

                QpsRecordBucket.getInstance().delTimeOut();
                try {
                    /**清理时间间隔*/
                    Thread.sleep(1000);
                } catch (Exception e) {
                }
            }
        }
    }




}
