package com.liveme.demo.limit;


public class LimitHelper {

    /**
     * 检查cmd是否已超限
     */
    public static boolean checkCmd(int cmd) {
        int count = 0;
        if (QpsRecordCache.getInstance().cmdExist(cmd)) {
            count = QpsRecordCache.getInstance().cmdCount(cmd,
                    QpsRecordCache.getInstance().getCmdCount(cmd)+1);
        } else {
            QpsRecordCache.getInstance().cmdCount(cmd,1);
        }
        if (count > QpsRecordCache.getInstance().getCmdCount(cmd)) {
            return false;
        }
        return true;
    }

    /**检查appID是否已超限*/
    public static boolean checkAppId(String appId){
        int count = 0;
        if (QpsRecordCache.getInstance().appIdExist(appId)) {
            count = QpsRecordCache.getInstance().appIdCount(appId,
                    QpsRecordCache.getInstance().getAppIdCount(appId)+1);
        } else {
            QpsRecordCache.getInstance().appIdCount(appId,1);
        }
        if (count > QpsRecordCache.getInstance().getAppIdCount(appId)) {
            return false;
        }
        return true;
    }


}
