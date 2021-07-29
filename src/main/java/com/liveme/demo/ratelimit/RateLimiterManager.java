package com.liveme.demo.ratelimit;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicBoolean;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.util.concurrent.RateLimiter;

/**
 * 业务限流1
 *
 * @author wgx
 * @version 2017年10月31日
 */
public class RateLimiterManager {

    private Logger log = LoggerFactory.getLogger(RateLimiterManager.class);

    // 高优先级消息限流
    public ConcurrentMap<String, RateLimiter> rateMap = new ConcurrentHashMap<String, RateLimiter>();

    /**
     * rateMap活动时间
     */
    public ConcurrentMap<String, Long> rateMapActiveTime = new ConcurrentHashMap<String, Long>();

    private static class InstanceHolder {
        public static RateLimiterManager instance = new RateLimiterManager();

        static {
            RatelimiterClear rcl = new RatelimiterClear(instance.rateMapActiveTime, instance.rateMap);
            Thread rclThread = new Thread(rcl);
            rclThread.setName("RatelimiterClear -clear");
            rclThread.start();
        }
    }

    private static String whiteRateKey = "-white";
    private static String commonRateKey = "-common";
    private static String hightRateKey = "-hight";

    public static RateLimiterManager getInstance() {
        return InstanceHolder.instance;
    }

    public String getCommonRateLimitKey(String roomId) {
        return roomId + commonRateKey;
    }

    public String getWhiteRateLimitKey(String roomId) {
        return roomId + whiteRateKey;
    }

    public String getHigthRateLimitKey(String roomId) {
        return roomId + hightRateKey;
    }


    /**
     * 非阻塞获取许可 true：获得许可 false：未获得许可 从RateLimiter 获取许可如果该许可可以在不超过timeout的时间内获取得到的话，
     * 或者如果无法在timeout 过期之前获取得到许可的话，那么立即返回false（无需等待）
     *
     * @param key
     * @return
     */
    public boolean tryAcquire(String key, double rateLimiterNum) {

        rateMapActiveTime.put(key, System.currentTimeMillis());

        // 已经加入限流
        if (rateMap.containsKey(key)) {

            if (rateMap.get(key).tryAcquire()) {
                return true;
            } else {
                return false;
            }

            // 未加入
        } else {

            RateLimiter keyRateLimiter = RateLimiter.create(rateLimiterNum);
            rateMap.put(key, keyRateLimiter);
            log.info("RateMap size()={},put dataId-level:{}", RateLimiterManager.getInstance().rateMap.size(), key);

            return true;

        }

    }

    /**
     * 阻塞式获取许可
     *
     * @param key
     * @return
     */
    public double acquire(String key, double rateLimiterNum) {

        // 已经加入限流
        if (rateMap.containsKey(key)) {

            return rateMap.get(key).acquire();

            // 未加入
        } else {

            RateLimiter keyRateLimiter = RateLimiter.create(rateLimiterNum);
            rateMap.put(key, keyRateLimiter);
            return 0;

        }
    }

    public void show() {
        log.info("直播限流数={}", rateMap.size());
        for (ConcurrentHashMap.Entry<String, RateLimiter> entry : rateMap.entrySet()) {
            log.info("                      {} {}", entry.getKey(), entry.getValue());
        }
    }

    /**
     * 清除限流对象
     *
     * @param dataId
     */
    public void removeRateLimiter(int dataId) {

        String roomId = String.valueOf(dataId);

        String whiteRateLimitKey = getWhiteRateLimitKey(roomId);

        String commonRateLimitKey = getCommonRateLimitKey(roomId);

        String hightRateLimitKey = getHigthRateLimitKey(roomId);

        RateLimiterManager.getInstance().rateMap.remove(whiteRateLimitKey);
        RateLimiterManager.getInstance().rateMap.remove(commonRateLimitKey);
        RateLimiterManager.getInstance().rateMap.remove(hightRateLimitKey);

        log.info("Clear room={} removeRateLimiter! rateMap.size()={}", dataId,
                RateLimiterManager.getInstance().rateMap.size());

    }

    /**
     * 整体清空限流对象
     */
    public int removeRateLimiter(boolean showSize) {
        if (showSize) {
            int size = RateLimiterManager.getInstance().rateMap.size();
            log.info("Search removeRateLimiter! rateMap.size()={}", RateLimiterManager.getInstance().rateMap.size());
            return size;
        } else {
            RateLimiterManager.getInstance().rateMap.clear();
            log.info("Clear removeRateLimiter! rateMap.size()={}", RateLimiterManager.getInstance().rateMap.size());
            return 0;
        }
    }

    /**
     * 消息限流 白名单消息限流：白名单消息可以得到令牌，再继续限制非白名单消息 非白名单消息限流：按速率限流
     *
     * @param packet
     */
    public boolean rateLimiter(String msgId, double whiteNum, double highNum, double commonNum) {

        String hightRateLimitKey = RateLimiterManager.getInstance().getHigthRateLimitKey(msgId);
        if (!RateLimiterManager.getInstance().tryAcquire(hightRateLimitKey, highNum)) {
            // 限流
            log.info("@@@@HighMsg Ratelimitered. w={}, h={}, c={}, packet={}", whiteNum, highNum, commonNum);
            return false;
        }

        return true;
    }

    public void test() {
        String key1 = "key1";
        for (int i = 1; i <= 50; i++) {

//			try {
//				Thread.sleep(200);
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}

            if (RateLimiterManager.getInstance().tryAcquire(key1, 50)) {
                log.info("Test1:{}", i);
            } else {
                log.error(".");
                // System.out.print(".");
            }
        }

        System.out.println("");
        log.info("------------------------------");

        String key2 = "key2";
        for (int i = 1; i <= 50; i++) {
            RateLimiterManager.getInstance().acquire(key2, 50);
            log.info("Test2:{}", i);
        }

    }

    public void test2() {
        Map<String, String> whiteMsgMap = new HashMap<String, String>();
        whiteMsgMap.put("", "");

        String key1 = "key1";

        boolean getAcquire = false;
        for (int i = 1; i <= 50; i++) {
            if (i <= 25) {
                if (RateLimiterManager.getInstance().tryAcquire(key1, 50)) {
                    log.info("GIFT {} : {}", i, i);
                    getAcquire = true;
                } else {
                    log.error(".");
                    // System.out.print(".");
                    getAcquire = false;
                }
            } else {
                if (getAcquire) {
                    if (RateLimiterManager.getInstance().tryAcquire(key1, 50)) {
                        log.info("MESSAGE {} : {}", i, i);
                    } else {
                        log.error(".");
                    }
                } else {
                    return;
                }
            }
        }
    }

    public static void main(String args[]) {
        RateLimiterManager r = new RateLimiterManager();
        String str = "-800058292,-35061049,-1029031118,351987643,1792193538,1572901211,994745999,1602620354," +
                "80368693,416118459,-8274866,-1963067388,1303759914,1142393659,-450939712,1604894574,807479392";
        String[] diyTypeArr = str.split(",");
        Integer diyType;
        for (int i = 0; i <= 50; i++) {
            if (i < diyTypeArr.length) {
                diyType = Integer.valueOf(diyTypeArr[i]);
            } else {
                diyType = i;
            }
            r.rateLimiter(i + "	" + diyType, 0, 10, 0);
        }
    }

}

class RatelimiterClear implements Runnable {

    private Logger log = LoggerFactory.getLogger(RatelimiterClear.class);

    // 大于24分钟没有活跃就清理，限流对象
//	private int clearTimeMills = 1000 * 60 * 24;
    private int clearTimeMills = 1000 * 20;

    private ConcurrentMap<String, Long> rateMapActiveTime;

    private ConcurrentMap<String, RateLimiter> rateMap;

    public RatelimiterClear(ConcurrentMap<String, Long> rateMapActiveTime, ConcurrentMap<String, RateLimiter> rateMap) {
        this.rateMap = rateMap;
        this.rateMapActiveTime = rateMapActiveTime;
        log.info("clearTimeMills:{},rateMap:{},rateMapActiveTime{}", clearTimeMills, rateMapActiveTime, rateMap);
    }

    @Override
    public void run() {
        try {
            while (true) {

                long currentTimeMills = System.currentTimeMillis();
                for (String key : rateMapActiveTime.keySet()) {
                    long activeTime = rateMapActiveTime.get(key);
                    if ((currentTimeMills - activeTime) > clearTimeMills) {
                        rateMapActiveTime.remove(key);
                        rateMap.remove(key);
                        if (log.isInfoEnabled()) {
                            log.info("rateMap clear key:{}", key);
                        }
                    }
                }
                Thread.sleep(10000);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}