package com.liveme.demo.limit;

import java.util.HashMap;
import java.util.Map;

public class AppIdConfig {


    private static class InstanceHolder {
        public static final AppIdConfig instance = new AppIdConfig();
    }

    public static AppIdConfig getInstance() {
        return InstanceHolder.instance;
    }

    private Map<String, Integer> appIdConfig = new HashMap<>();


    /**
     * 注册配置
     */
    public void config(String appId, Integer count) {
        appIdConfig.put(appId, count);
    }


    /**获取配置*/
    public  Integer getCountByAppId(String appId) {
        return appIdConfig.get(appId);
    }
}
