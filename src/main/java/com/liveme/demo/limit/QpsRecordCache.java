package com.liveme.demo.limit;

import java.util.concurrent.ConcurrentHashMap;

public class QpsRecordCache {



    private static class InstanceHolder {
        public static final QpsRecordCache instance = new QpsRecordCache();
    }

    public static QpsRecordCache getInstance() {
        return QpsRecordCache.InstanceHolder.instance;
    }



    private static ConcurrentHashMap<String, Integer> limitAppIdMap = new ConcurrentHashMap<>();
    private static ConcurrentHashMap<Integer, Integer> limitCMDMap = new ConcurrentHashMap<>();


    /**设置cmd 计数*/
    public Integer cmdCount(Integer cmd,Integer count){
        return limitCMDMap.put(cmd,count);
    }

    /**获取count */
    public Integer getCmdCount(Integer cmd){
        return limitCMDMap.get(cmd);
    }

    /**是否存在 */
    public boolean cmdExist(Integer cmd){
        return limitCMDMap.contains(cmd);
    }

    /**重置 */
    public void cmdReset(Integer cmd){
         limitCMDMap.remove(cmd);
    }






    /**设置appId计数*/
    public Integer appIdCount(String appId,Integer count){
        return limitAppIdMap.put(appId,count);
    }

    /**获取appIdcount */
    public Integer getAppIdCount(String appId){
        return limitAppIdMap.get(appId);
    }

    /**appId是否存在 */
    public boolean appIdExist(String appId){
        return limitAppIdMap.contains(appId);
    }

    /**重置appId */
    public void appIdReset(String appId){
        limitAppIdMap.remove(appId);
    }








}
