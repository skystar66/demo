package com.liveme.demo.limit;

import java.util.HashMap;
import java.util.Map;

public class CMDConfig {


    private static class InstanceHolder {
        public static final CMDConfig instance = new CMDConfig();
    }

    public static CMDConfig getInstance() {
        return InstanceHolder.instance;
    }

    private Map<Integer, Integer> cmdConfig = new HashMap<>();


    /**
     * 注册配置
     */
    public void config(Integer cmd, Integer count) {
        cmdConfig.put(cmd, count);
    }


    /**获取配置*/
    public  Integer getCountByCmd(Integer cmd) {
        return cmdConfig.get(cmd);
    }
}
