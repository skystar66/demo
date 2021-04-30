package com.liveme.demo.redis;

//import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.*;

/**
 * Redis操作接口
 *
 * @version 2.0
 * @author xl
 * @date 20210407 11:06
 */
public class RedisAPI {

    private static final Logger logger = LoggerFactory.getLogger(RedisAPI.class);

    private static JedisPool pool = null;// 写服务器

    /**
     * 构建redis连接池
     *
     * @param ip
     * @param port
     * @return JedisPool
     */
    public synchronized static JedisPool initPool(String ip, int port, int maxTotal, int maxIdle,
                                                  long maxWaitMillis) {
        if (pool == null) {
            JedisPoolConfig config = new JedisPoolConfig();
            // 控制一个pool可分配多少个jedis实例，通过pool.getResource()来获取；
            // 如果赋值为-1，则表示不限制；如果pool已经分配了maxActive个jedis实例，则此时pool的状态为exhausted(耗尽)。
            config.setMaxTotal(maxTotal);
            // 控制一个pool最多有多少个状态为idle(空闲的)的jedis实例。
            config.setMaxIdle(maxIdle);
            // 表示当borrow(引入)一个jedis实例时，最大的等待时间，如果超过等待时间，则直接抛出JedisConnectionException；
            config.setMaxWaitMillis(maxWaitMillis);
            pool = new JedisPool(config, ip, port, 100000);
            // pool = new JedisPool(config, GetRedisConfigUtil.getUrl(),
            // GetRedisConfigUtil.getPort(), 100000);
        }
        return pool;
    }

    /**
     * 返还到连接池
     *
     * @param pool
     * @param redis
     */
    public static void returnResource(JedisPool pool, Jedis redis) {
        if (redis != null) {
//			pool.returnResource(redis);
            redis.close();
        }
    }

    /**
     * 把keyLike开头的key设置成同一个value
     *
     * @param keyLike
     * @param value
     * @return
     */
    public static boolean setkeysLikeSameValue(final String keyLike, final String value) {
        long count = 0;

        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            Set<String> keys = jedis.keys(keyLike + "*");
            Iterator<String> iter = keys.iterator();
            while (iter.hasNext()) {
                String key = iter.next();
                jedis.setex(key, 60 * 60 * 24, value);
            }
        } catch (Exception e) {
            // 释放redis对象
            returnResource(pool, jedis);
            logger.error("some Expetions:",e);
            return false;
        } finally {
            // 返还到连接池
            returnResource(pool, jedis);
        }

        return true;
    }

    /**
     * 模糊删除key
     *
     * @param keyLike
     * @return
     */
    public static long delkeysLike(final String keyLike) {
        long count = 0;

        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            Set<String> keys = jedis.keys(keyLike + "*");
            Iterator<String> iter = keys.iterator();
            while (iter.hasNext()) {
                String key = iter.next();
                logger.info("删除【 {} 】",key);
                count += jedis.del(key);
            }
            return count;
        } catch (Exception e) {
            // 释放redis对象
            returnResource(pool, jedis);
            logger.error("some Expetions:",e);
        } finally {
            // 返还到连接池
            returnResource(pool, jedis);
        }

        return count;
    }

    /**
     * 获取数据
     *
     * @param key
     * @return
     */
    public static String get(String key) {
        String value = null;


        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            value = jedis.get(key);
        } catch (Exception e) {
            // 释放redis对象
            returnResource(pool, jedis);
            logger.error("some Expetions:",e);
        } finally {
            // 返还到连接池
            returnResource(pool, jedis);
        }

        return value;
    }

    /**
     * 存储数据
     *
     * @param key
     * @return
     */
    public static void set(String key, String value) {
        if (StringUtils.isEmpty(key) || StringUtils.isEmpty(value)) {
            return;
        }

        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            jedis.set(key, value);

        } catch (Exception e) {
            // 释放redis对象
            returnResource(pool, jedis);
            logger.error("some Expetions:",e);
        } finally {
            // 返还到连接池
            returnResource(pool, jedis);
        }
    }

    /**
     * 描述:元素递增
     *
     * @param key
     *
     */
    public static Long incr(String key) {
        if (StringUtils.isEmpty(key)) {
            return null;
        }

        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            return jedis.incr(key);
        } catch (Exception e) {
            // 释放redis对象
            returnResource(pool, jedis);
            logger.error("some Expetions:",e);
        } finally {
            // 返还到连接池
            returnResource(pool, jedis);
        }
        return null;
    }

    /**
     * 描述:元素递增
     *
     * @param key
     *
     */
    public static Long hincrBy(String key, String field) {
        if (StringUtils.isEmpty(key)) {
            return null;
        }

        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            return jedis.hincrBy(key, field, 1);
        } catch (Exception e) {
            // 释放redis对象
            returnResource(pool, jedis);
            logger.error("some Expetions:",e);
        } finally {
            // 返还到连接池
            returnResource(pool, jedis);
        }
        return null;
    }

    public static void expire(String key, int seconds) {
        if (StringUtils.isEmpty(key)) {
            return;
        }

        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            jedis.expire(key, seconds);
        } catch (Exception e) {
            // 释放redis对象
            returnResource(pool, jedis);
            logger.error("some Expetions:",e);
        } finally {
            // 返还到连接池
            returnResource(pool, jedis);
        }
    }

    /**
     * 保存key，并且设置过期时间
     *
     * @param key
     * @param time
     *            秒
     * @param value
     */
    public static void setex(String key, int time, String value) {
        if (StringUtils.isEmpty(key) || StringUtils.isEmpty(value)) {
            return;
        }


        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            jedis.setex(key, time, value);
            // jedis.expire(key, time);
        } catch (Exception e) {
            // 释放redis对象
            returnResource(pool, jedis);
            logger.error("save 出错：", e);
        } finally {
            // 返还到连接池
            returnResource(pool, jedis);
        }
    }

    /**
     * 保存key，并且设置过期时间
     *
     * @param key
     * @param time
     *            秒
     * @param value
     */
    public static void lpush(String key, String value) {
        if (StringUtils.isEmpty(key) || StringUtils.isEmpty(value)) {
            return;
        }


        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            jedis.lpush(key, value);
        } catch (Exception e) {
            // 释放redis对象
            returnResource(pool, jedis);
            logger.error("lpush Expetions:",e);
        } finally {
            // 返还到连接池
            returnResource(pool, jedis);
        }
    }

    public static List<String> getAllList(String key) {
        List<String> value = new ArrayList<String>();
        if (StringUtils.isEmpty(key)) {
            return value;
        }


        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            value = jedis.lrange(key, 0, -1);
        } catch (Exception e) {
            // 释放redis对象
            returnResource(pool, jedis);
            logger.error("getAllList Expetions:",e);
        } finally {
            // 返还到连接池
            returnResource(pool, jedis);
        }
        return value;
    }

    /**
     * 获取list中最后一条
     *
     * @param key
     * @return
     */
    public static String getListLastOne(String key) {
        String value = null;
        if (StringUtils.isEmpty(key)) {
            return value;
        }


        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            List<String> list = jedis.lrange(key, 0, 0);
            if (!CollectionUtils.isEmpty(list)) {
                return list.get(0);
            }
        } catch (Exception e) {
            // 释放redis对象
            returnResource(pool, jedis);
            logger.error("getListLastOne Expetions:",e);
        } finally {
            // 返还到连接池
            returnResource(pool, jedis);
        }
        return value;
    }

    /**
     * 获取用户最后一个订单号
     *
     * @param key
     * @return
     */
    public static List<String> getLastOrder(String key) {
        List<String> value = new ArrayList<String>();
        if (StringUtils.isEmpty(key)) {
            return value;
        }


        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            value = jedis.lrange(key, 0, 0);
        } catch (Exception e) {
            // 释放redis对象
            returnResource(pool, jedis);
            logger.error("getLastOrder Expetions:",e);
        } finally {
            // 返还到连接池
            returnResource(pool, jedis);
        }
        return value;
    }

    /**
     * 设置集合的多个值，放入Set集合
     *
     * @param key
     * @param value
     */
    public static void sadd(String key, String value) {
        if (StringUtils.isEmpty(key) || StringUtils.isEmpty(value)) {
            return;
        }

        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            jedis.sadd(key, value);
        } catch (Exception e) {
            // 释放redis对象
            returnResource(pool, jedis);
            logger.error("sadd Expetions:",e);
        } finally {
            // 返还到连接池
            returnResource(pool, jedis);
        }
    }

    /**
     * 设置Map值
     *
     * @param key
     * @param value
     */
    public static void hset(String key, String field, String value) {
        if (StringUtils.isEmpty(key) || StringUtils.isEmpty(value) || StringUtils.isEmpty(field)) {
            return;
        }

        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            long rst = jedis.hset(key, field, value);
        } catch (Exception e) {
            // 释放redis对象
            returnResource(pool, jedis);
            logger.error("hset Expetions:",e);
        } finally {
            // 返还到连接池
            returnResource(pool, jedis);
        }
    }


    /**
     * 设置Map值
     *
     * @param key
     * @param value
     */
    public static void hset(byte[] key, byte[] field, byte[] value) {
        if (StringUtils.isEmpty(key) || StringUtils.isEmpty(value) || StringUtils.isEmpty(field)) {
            return;
        }

        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            long rst = jedis.hset(key, field, value);
        } catch (Exception e) {
            // 释放redis对象
            returnResource(pool, jedis);
            logger.error("hset Expetions:",e);
        } finally {
            // 返还到连接池
            returnResource(pool, jedis);
        }
    }



    /**
     * 从Map中获取value
     *
     * @param key
     * @param field
     * @return
     */
    public static String hget(String key, String field) {
        String value = null;
        if (StringUtils.isEmpty(key) || StringUtils.isEmpty(field)) {
            return value;
        }
//
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            value = jedis.hget(key, field);
        } catch (Exception e) {
            // 释放redis对象
            returnResource(pool, jedis);
            logger.error("hget Expetions:",e);
        } finally {
            // 返还到连接池
            returnResource(pool, jedis);
        }
        return value;
    }




    /**
     * 从Map中获取value
     *
     * @param key
     * @param field
     * @return
     */
    public static byte[] hget(byte[] key, byte[] field) {
        byte[] value = null;
        if (StringUtils.isEmpty(key) || StringUtils.isEmpty(field)) {
            return value;
        }
//
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            value = jedis.hget(key, field);
        } catch (Exception e) {
            // 释放redis对象
            returnResource(pool, jedis);
            logger.error("hget Expetions:",e);
        } finally {
            // 返还到连接池
            returnResource(pool, jedis);
        }
        return value;
    }



    /**
     * 从Map中获取value
     *
     * @param key
     * @param field
     * @return
     */
    public static Long hdel(String key, String... field) {
        Long value = null;
        if (StringUtils.isEmpty(key) || StringUtils.isEmpty(field)) {
            return value;
        }
//
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            value=jedis.hdel(key, field);
        } catch (Exception e) {
            // 释放redis对象
            returnResource(pool, jedis);
            logger.error("hdel Expetions:",e);
        } finally {
            // 返还到连接池
            returnResource(pool, jedis);
        }
        return value;
    }

    /**
     * 从Map中获取value
     *
     * @param key
     * @param field
     * @return
     */
    public static Long hdel(byte[] key, byte[]... field) {
        Long value = null;
        if (StringUtils.isEmpty(key) || StringUtils.isEmpty(field)) {
            return value;
        }
//
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            value=jedis.hdel(key, field);
        } catch (Exception e) {
            // 释放redis对象
            returnResource(pool, jedis);
            logger.error("hdel Expetions:",e);
        } finally {
            // 返还到连接池
            returnResource(pool, jedis);
        }
        return value;
    }



    /**
     * 获取Map值
     *
     * @param key
     * @param value
     */
    @SuppressWarnings("rawtypes")
    public static Map hgetAll(String key) {
        if (StringUtils.isEmpty(key)) {
            return null;
        }
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            Map map = jedis.hgetAll(key);
            return map;
        } catch (Exception e) {
            // 释放redis对象
            returnResource(pool, jedis);
            logger.error("hgetAll Expetions:",e);
        } finally {
            // 返还到连接池
            returnResource(pool, jedis);
        }
        return null;
    }

    /**
     * 获取集合类的值
     *
     * @param key
     * @return
     */
    @SuppressWarnings("rawtypes")
    public static Set getSmembers(String key) {
        if (StringUtils.isEmpty(key)) {
            return null;
        }
        Set value = null;
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            value = jedis.smembers(key);
        } catch (Exception e) {
            // 释放redis对象
            returnResource(pool, jedis);
            logger.error("getSmembers Expetions:",e);
        } finally {
            // 返还到连接池
            returnResource(pool, jedis);
        }

        return value;
    }

    /**
     * 从List中删除指定的value
     *
     * @param key
     * @param value
     */
    public static Long lrem(String key, String value) {
        Long ret = 0L;
        if (StringUtils.isEmpty(key) || StringUtils.isEmpty(value)) {
            return ret;
        }

        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            ret = jedis.lrem(key, 0, value); // 从List中移除所有指定value的内容
        } catch (Exception e) {
            // 释放redis对象
            returnResource(pool, jedis);
            logger.error("lrem Expetions:",e);
        } finally {
            // 返还到连接池
            returnResource(pool, jedis);
        }
        return ret;
    }

    /**
     * 给制定频道发布消息
     *
     * @param channel
     * @param message
     * @return
     */
    public static Long publish(String channel, String message) {
        Long ret = 0L;
        if (StringUtils.isEmpty(channel) || StringUtils.isEmpty(message)) {
            return ret;
        }

        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            ret = jedis.publish(channel, message);
        } catch (Exception e) {
            // 释放redis对象
            returnResource(pool, jedis);
            logger.error("publish Expetions:",e);
        } finally {
            // 返还到连接池
            returnResource(pool, jedis);
        }
        return ret;
    }

    /**
     * 删除一个key
     *
     * @param key
     * @param value
     */
    public static Long del(String key) {
        Long ret = 0L;
        if (StringUtils.isEmpty(key)) {
            return ret;
        }

        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            ret = jedis.del(key);
        } catch (Exception e) {
            // 释放redis对象
            returnResource(pool, jedis);
            logger.error("del Expetions:",e);
        } finally {
            // 返还到连接池
            returnResource(pool, jedis);
        }
        return ret;
    }

    /**
     * 获取keys
     *
     * @author xl
     * @param pattern
     * @return
     */
    public static Set<String> getKeys(String pattern) {
        Set<String> keys = new HashSet<String>();

        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            keys = jedis.keys(pattern);
        } catch (Exception e) {
            // 释放redis对象
            returnResource(pool, jedis);
            logger.error("getKeys Expetions:",e);
        } finally {
            // 返还到连接池
            returnResource(pool, jedis);
        }
        return keys;
    }

    /**
     * 删除keys
     *
     * @author xl
     * @param pattern
     * @return
     */
    public static long delKeys(String... keys) {

        Jedis jedis = null;
        long i = 0L;
        try {
            jedis = pool.getResource();
            i = jedis.del(keys);
        } catch (Exception e) {
            // 释放redis对象
            returnResource(pool, jedis);
            logger.error("delKeys Expetions:",e);
        } finally {
            // 返还到连接池
            returnResource(pool, jedis);
        }
        return i;
    }

    /**
     * 获取key
     *
     * @author xl
     * @param pattern
     * @return
     */
    public static String getsetKey(String key, String value) {
        if (StringUtils.isEmpty(value) || StringUtils.isEmpty(value)) {
            return null;
        }


        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            String oldValue = jedis.getSet(key, value);
            return oldValue;
        } catch (Exception e) {
            // 释放redis对象
            returnResource(pool, jedis);
            logger.error("getsetKey Expetions:",e);
        } finally {
            // 返还到连接池
            returnResource(pool, jedis);
        }
        return null;
    }

    /**
     * 获取key
     *
     * @author xl
     * @param pattern
     * @return
     */
    public static String getsetKey2compareTime(String compareTimekey, String value) {
        if (StringUtils.isEmpty(compareTimekey) || StringUtils.isEmpty(value)) {
            return null;
        }


        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            compareTimekey = "Rep" + compareTimekey;
            String oldValue = jedis.getSet(compareTimekey, value);
            return oldValue;
        } catch (Exception e) {
            // 释放redis对象
            returnResource(pool, jedis);
            logger.error("getsetKey2compareTime Expetions:",e);
        } finally {
            // 返还到连接池
            returnResource(pool, jedis);
        }
        String timeNow = String.valueOf(System.currentTimeMillis());
        return timeNow;
    }

    /**
     * 针对key增加指定value
     *
     * @param key
     * @param value
     * @return key+value
     * @author xl
     */
    public static Long incrby(String key, Long value) {

        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            Long newValue = jedis.incrBy(key, value);
            return newValue;
        } catch (Exception e) {
            // 释放redis对象
            returnResource(pool, jedis);
            logger.error("incrby Expetions:",e);
        } finally {
            // 返还到连接池
            returnResource(pool, jedis);
        }
        return null;

    }

    /**
     * 针对key减去指定value
     *
     * @param key
     * @param value
     * @return key-value
     * @author xl
     */
    public static Long decrby(String key, Long value) {


        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            Long newValue = jedis.decrBy(key, value);
            return newValue;
        } catch (Exception e) {
            // 释放redis对象
            returnResource(pool, jedis);
            logger.error("decrby Expetions:",e);
        } finally {
            // 返还到连接池
            returnResource(pool, jedis);
        }
        return null;
    }

    /**
     * 向对应sortedSet里插入指定权重的元素
     *
     * @param sortedSetName
     * @param elementScore
     *            元素权重
     * @param elementname
     *            元素名
     * @return
     */
    public static Long sortedSetAdd(String sortedSetName, double elementScore, String elementname) {

        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            Long result = jedis.zadd(sortedSetName, elementScore, elementname);
            return result;
        } catch (Exception e) {
            // 释放redis对象
            returnResource(pool, jedis);
            logger.error("sortedSetAdd Expetions:",e);
        } finally {
            // 返还到连接池
            returnResource(pool, jedis);
        }
        return null;
    }

    /**
     * 删除指定sortedSet的元素
     *
     * @param sortedSetName
     * @param elementname
     *            元素组
     * @return
     */
    public static Long sortedSetRemove(String sortedSetName, String... elementname) {

        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            Long result = jedis.zrem(sortedSetName, elementname);
            return result;
        } catch (Exception e) {
            // 释放redis对象
            returnResource(pool, jedis);
            logger.error("sortedSetRemove Expetions:",e);
        } finally {
            // 返还到连接池
            returnResource(pool, jedis);
        }
        return null;
    }

    /**
     * 取得指定sortedSet的全部元素（默认升序）
     *
     * @param sortedSetName
     * @return
     */
    public static Set<String> sortedRang(String sortedSetName) {

        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            Set<String> result = jedis.zrange(sortedSetName, 0, -1);
            return result;
        } catch (Exception e) {
            // 释放redis对象
            returnResource(pool, jedis);
            logger.error("sortedRang Expetions:",e);
        } finally {
            // 返还到连接池
            returnResource(pool, jedis);
        }
        return null;
    }

    /**
     * 取得指定sortedSet的全部元素（默认升序）
     *
     * @param sortedSetName
     * @param start
     *            其实index
     * @param end
     *            终止index
     * @return
     */
    public static Set<String> sortedRang(String sortedSetName, Long start, Long end) {

        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            Set<String> result = jedis.zrange(sortedSetName, start, end);
            return result;
        } catch (Exception e) {
            // 释放redis对象
            returnResource(pool, jedis);
            logger.error("sortedRang Expetions:",e);
        } finally {
            // 返还到连接池
            returnResource(pool, jedis);
        }
        return null;
    }

    /**
     * 活动指定sortedSet的某元素的排名 未找到指定元素时返回-1
     *
     * @param sortedSetName
     * @param elementName
     * @return
     */
    public static Long getRang(String sortedSetName, String elementName) {

        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            Long rank = jedis.zrank(sortedSetName, elementName);
            if (rank == null) {
                return -1L;
            } else {
                return rank;
            }
        } catch (Exception e) {
            // 释放redis对象
            returnResource(pool, jedis);
            logger.error("getRang Expetions:",e);
        } finally {
            // 返还到连接池
            returnResource(pool, jedis);
        }
        return -1L;
    }

    /**
     * 对特定setName的set增加一个或多个值，返回是增加元素的个数。 注意：对同一个member多次add，set中只会保留一份。
     *
     * @param setName
     * @param members
     * @return
     */
    public static long sadd(String setName, String... members) {

        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            Long rank = jedis.sadd(setName, members);
            if (rank == null) {
                return -1L;
            } else {
                return rank;
            }
        } catch (Exception e) {
            // 释放redis对象
            returnResource(pool, jedis);
            logger.error("sadd Expetions:",e);
        } finally {
            // 返还到连接池
            returnResource(pool, jedis);
        }
        return -1L;
    }

    /**
     * 判断值是否是set的member。 如果值是set的member返回true，否则，返回false
     *
     * @param setName
     * @param member
     * @return
     */
    public static boolean sismember(String setName, String member) {

        Jedis jedis = null;
        Boolean exists = false;
        try {
            jedis = pool.getResource();
            exists = jedis.sismember(setName, member);
        } catch (Exception e) {
            // 释放redis对象
            returnResource(pool, jedis);
            logger.error("sismember Expetions:",e);
        } finally {
            // 返还到连接池
            returnResource(pool, jedis);
        }
        return exists;
    }

    /**
     * 从set里随机一个变量
     *
     * @param setName
     * @return
     */
    public static String srandomber(String setName) {

        Jedis jedis = null;
        String value = null;
        try {
            jedis = pool.getResource();
            value = jedis.srandmember(setName);
        } catch (Exception e) {
            // 释放redis对象
            returnResource(pool, jedis);
            logger.error("srandomber Expetions:",e);
        } finally {
            // 返还到连接池
            returnResource(pool, jedis);
        }
        return value;

    }

    /**
     * 移除一个或多个member
     *
     * @param setName
     * @param members
     * @return
     */
    public static long srem(String setName, String... members) {

        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            Long rank = jedis.srem(setName, members);
            if (rank == null) {
                return -1L;
            } else {
                return rank;
            }
        } catch (Exception e) {
            // 释放redis对象
            returnResource(pool, jedis);
            logger.error("srem Expetions:",e);
        } finally {
            // 返还到连接池
            returnResource(pool, jedis);
        }
        return -1L;
    }

    public static Set<String> keys(String pattern) {

        Jedis jedis = null;
        Set<String> value = null;
        try {
            jedis = pool.getResource();
            value = jedis.keys(pattern);
        } catch (Exception e) {
            // 释放redis对象
            returnResource(pool, jedis);
            logger.error("keys Expetions:",e);
        } finally {
            // 返还到连接池
            returnResource(pool, jedis);
        }
        return value;
    }
}