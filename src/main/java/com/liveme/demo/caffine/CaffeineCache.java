package com.liveme.demo.caffine;

import com.github.benmanes.caffeine.cache.*;
import com.google.common.testing.FakeTicker;
import com.liveme.demo.redis.point.Point;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

public class CaffeineCache {






    static Cache<String, Object> manualCache = Caffeine.newBuilder()
            .expireAfterWrite(10, TimeUnit.MINUTES)
            .maximumSize(10000)
            .build();

    static LoadingCache<String, Object> loadingCache = Caffeine.newBuilder()
            .maximumSize(10000)
            .expireAfterWrite(10, TimeUnit.MINUTES)
            .build(key -> createExpensiveGraph(key));

    static AsyncLoadingCache<String, Object> asyncLoadingCache = Caffeine.newBuilder()
            .maximumSize(10000)
            .expireAfterWrite(10, TimeUnit.MINUTES)
            // Either: Build with a synchronous computation that is wrapped as asynchronous
            .buildAsync(key -> createExpensiveGraph(key));
    // Or: Build with a asynchronous computation that returns a future
    // .buildAsync((key, executor) -> createExpensiveGraphAsync(key, executor));

    private CompletableFuture<Object> createExpensiveGraphAsync(String key, Executor executor) {
        CompletableFuture<Object> objectCompletableFuture = new CompletableFuture<>();
        return objectCompletableFuture;
    }

    private static Object createExpensiveGraph(String key) {
        System.out.println("缓存不存在或过期，调用了createExpensiveGraph方法获取缓存key的值");
        if (key.equals("name")) {
            throw new RuntimeException("调用了该方法获取缓存key的值的时候出现异常");
        }
        return key+"-value";
    }

    private static List<Integer> createCosts(String key) {
        System.out.println("缓存不存在或过期，调用了createExpensiveGraph方法获取缓存key的值");

        List<Integer> costs = new ArrayList<>();
        return costs;
    }


    /**
     * cache 缓存
     */
    private static Cache<String, Map<String, Object>> cache;

    static {
        cache = Caffeine.newBuilder()
                .maximumSize(64)
                //指定缓存在写入多久后失效。
                .expireAfterWrite(10*60*1000, TimeUnit.SECONDS)
                .build();
    }



    public static void main(String[] args) {



        String key = "test";
        Point point = new Point();
        point.setValue(12.0);
        point.setTimestamp(System.currentTimeMillis());
        Map<String, Object> attachMap = cache.get(key, k -> {
            Map<String, Object> map = new HashMap<>();
            map.put(key, point);
            return map;
        });


        Point point2 = new Point();
        point2.setTimestamp(System.currentTimeMillis());
        point2.setValue(10.8);

        attachMap.put("test2", point2);


        for (Map.Entry<String, Object> stringObjectEntry : cache.getIfPresent(key).entrySet()) {
            System.out.println(stringObjectEntry.getKey());
        }

        //        testManual();

//        System.out.println(testLoading());
//        System.out.println(testSizeBased());
//        System.out.println(testTimeBased());
//        System.out.println(testRemoval());
//        System.out.println(testRefresh());
//        System.out.println(testStatistics());

//        testAsync();
//        System.out.println(testStatistics());

    }

    public static Object testManual() {
        String key = "name1";
        Object graph = null;

        // 根据key查询一个缓存，如果没有返回NULL
        graph = manualCache.getIfPresent(key);
        // 根据Key查询一个缓存，如果没有调用createExpensiveGraph方法，并将返回值保存到缓存。
        // 如果该方法返回Null则manualCache.get返回null，如果该方法抛出异常则manualCache.get抛出异常
        graph = manualCache.get(key, k -> createExpensiveGraph(k));
        // 将一个值放入缓存，如果以前有值就覆盖以前的值
        manualCache.put(key, graph);
        // 删除一个缓存
        manualCache.invalidate(key);

        ConcurrentMap<String, Object> map = manualCache.asMap();
        System.out.println(map.toString());
        return graph;
    }

    public static Object testLoading() {
        String key = "name1";

        // 采用同步方式去获取一个缓存和上面的手动方式是一个原理。在build Cache的时候会提供一个createExpensiveGraph函数。
        // 查询并在缺失的情况下使用同步的方式来构建一个缓存
        Object graph = loadingCache.get(key);

        // 获取组key的值返回一个Map
        List<String> keys = new ArrayList<>();
        keys.add(key);
        Map<String, Object> graphs = loadingCache.getAll(keys);
        System.out.println("graphs:"+graphs);
        return graph;
    }

    public static Object testSizeBased() {
        Cache<String, Object> cache = Caffeine.newBuilder()
                .maximumSize(1)
                .build();

//        cache.get("A");
//        System.out.println(cache.estimatedSize());
//        cache.get("B");
//        // 因为执行回收的方法是异步的，所以需要调用该方法，手动触发一次回收操作。
//        cache.cleanUp();
//        System.out.println(cache.estimatedSize());

        ExecutorService executorService = Executors.newFixedThreadPool(10);


        for (int k=0;k<10;k++) {
            for (int i=0;i<10;i++) {
                int finalI = i;
                executorService.submit(new Runnable() {
                    @Override
                    public void run() {
                        getValueFromRedis(""+ finalI,cache);
                    }
                });
            }
        }

        System.out.println(cache.estimatedSize());
        System.out.println(cache.asMap().size());
        return "";
    }


    public static Object getValueFromRedis(String key , Cache cache){
        Object obj =cache.getIfPresent(key);
        if (obj == null) {
            System.out.println("查询redis！！！！！key:"+key);
            cache.put(key,"value");
        }
        return cache.getIfPresent(key);
    }


    public static Object testTimeBased() {
        String key = "name1";
        // 用户测试，一个时间源，返回一个时间值，表示从某个固定但任意时间点开始经过的纳秒数。
        FakeTicker ticker = new FakeTicker();

        // 基于固定的到期策略进行退出
        // expireAfterAccess
        LoadingCache<String, Object> cache1 = Caffeine.newBuilder()
                .ticker(ticker::read)
                .expireAfterAccess(5, TimeUnit.SECONDS)
                .build(k -> createExpensiveGraph(k));

        System.out.println("expireAfterAccess：第一次获取缓存:"+cache1.get(key));
//        cache1.get(key);

        // 直接指定时钟
        ticker.advance(4900, TimeUnit.MILLISECONDS);
        System.out.println("expireAfterAccess：等待4.9S后，第二次次获取缓存:"+cache1.get(key));


        ticker.advance(101, TimeUnit.MILLISECONDS);
        System.out.println("expireAfterAccess：等待0.101S后，第三次次获取缓存:"+cache1.get(key));


        // expireAfterWrite
        LoadingCache<String, Object> cache2 = Caffeine.newBuilder()
                .ticker(ticker::read)
                .expireAfterWrite(5, TimeUnit.SECONDS)
                .build(k -> createExpensiveGraph(k));

        System.out.println("expireAfterWrite：第一次获取缓存:"+cache2.get(key));

        ticker.advance(4900, TimeUnit.MILLISECONDS);
        System.out.println("expireAfterWrite：等待4.9S后，第二次次获取缓存:"+ cache2.get(key));

        ticker.advance(101, TimeUnit.MILLISECONDS);
        System.out.println("expireAfterWrite：等待0.101S后，第三次次获取缓存:"+cache2.get(key));


        // Evict based on a varying expiration policy
        // 基于不同的到期策略进行退出
        LoadingCache<String, Object> cache3 = Caffeine.newBuilder()
                .ticker(ticker::read)
                .expireAfter(new Expiry<String, Object>() {

                    @Override
                    public long expireAfterCreate(String key, Object value, long currentTime) {
                        // Use wall clock time, rather than nanotime, if from an external resource
                        System.out.println("调用了 expireAfterCreate：设置缓存过期时间 5s：");

                        return TimeUnit.SECONDS.toNanos(5);
                    }
                    @Override
                    public long expireAfterUpdate(String key, Object graph,
                                                  long currentTime, long currentDuration) {
                        System.out.println("调用了 expireAfterUpdate：" + TimeUnit.NANOSECONDS.toMillis(currentDuration));
                        return currentDuration;
                    }

                    @Override
                    public long expireAfterRead(String key, Object graph,
                                                long currentTime, long currentDuration) {

                        System.out.println("调用了 expireAfterRead：" + TimeUnit.NANOSECONDS.toMillis(currentDuration));
                        return currentDuration;
                    }
                })
                .build(k -> createExpensiveGraph(k));

        System.out.println("expireAfter：第一次获取缓存:"+cache3.get(key));
        ticker.advance(4900, TimeUnit.MILLISECONDS);
        System.out.println("expireAfter：等待4.9S后，第二次次获取缓存:"+ cache3.get(key));


        ticker.advance(101, TimeUnit.MILLISECONDS);
        Object object = cache3.get(key);
        System.out.println("expireAfter：等待0.101S后，第三次次获取缓存:"+object);

        return object;
    }



    public static Object testRemoval() {
        String key = "name1";
        // 用户测试，一个时间源，返回一个时间值，表示从某个固定但任意时间点开始经过的纳秒数。
        FakeTicker ticker = new FakeTicker();

        // 基于固定的到期策略进行退出
        // expireAfterAccess
        LoadingCache<String, Object> cache = Caffeine.newBuilder()
                .removalListener((String k, Object graph, RemovalCause cause) ->
                        System.out.printf("Key %s was removed (%s)%n", k, cause))
                .ticker(ticker::read)
                .expireAfterAccess(5, TimeUnit.SECONDS)
                .build(k -> createExpensiveGraph(k));

        Object object = cache.get(key);

        System.out.println("第一次获取缓存:"+object);

        // 直接指定时钟
        ticker.advance(6000, TimeUnit.MILLISECONDS);
        System.out.println("等待6S后，第二次次获取缓存:"+cache.get(key));

        System.out.println("手动删除缓存");
        cache.invalidate(key);

        return object;
    }

    public static Object testRefresh() {
        String key = "name1";
        // 用户测试，一个时间源，返回一个时间值，表示从某个固定但任意时间点开始经过的纳秒数。
        FakeTicker ticker = new FakeTicker();

        // 基于固定的到期策略进行退出
        // expireAfterAccess
        LoadingCache<String, Object> cache = Caffeine.newBuilder()
                .removalListener((String k, Object graph, RemovalCause cause) ->
                        System.out.printf("执行移除监听器- Key %s was removed (%s)%n", k, cause))
                .ticker(ticker::read)
                .expireAfterWrite(5, TimeUnit.SECONDS)
                // 指定在创建缓存或者最近一次更新缓存后经过固定的时间间隔，刷新缓存
                .refreshAfterWrite(4, TimeUnit.SECONDS)
                .build(k -> createExpensiveGraph(k));

        Object object = cache.get(key);

        System.out.println("第一次获取缓存:"+object);

        // 直接指定时钟
        ticker.advance(4100, TimeUnit.MILLISECONDS);
        System.out.println("等待4.1S后，第二次次获取缓存:"+cache.get(key));

        // 直接指定时钟
        ticker.advance(5100, TimeUnit.MILLISECONDS);
        System.out.println("等待5.1S后，第三次次获取缓存:"+cache.get(key));

        return object;
    }

    public static Object testWriter() {
        String key = "name1";
        // 用户测试，一个时间源，返回一个时间值，表示从某个固定但任意时间点开始经过的纳秒数。
        FakeTicker ticker = new FakeTicker();

        // 基于固定的到期策略进行退出
        // expireAfterAccess
        LoadingCache<String, Object> cache = Caffeine.newBuilder()
                .removalListener((String k, Object graph, RemovalCause cause) ->
                        System.out.printf("执行移除监听器- Key %s was removed (%s)%n", k, cause))
                .ticker(ticker::read)
                .expireAfterWrite(5, TimeUnit.SECONDS)
                .writer(new CacheWriter<String, Object>() {
                    @Override
                    public void write(String key, Object graph) {
                        // write to storage or secondary cache
                        // 写入存储或者二级缓存
                        System.out.printf("testWriter:write - Key %s was write (%s)%n", key, graph);
                        createExpensiveGraph(key);
                    }

                    @Override
                    public void delete(String key, Object graph, RemovalCause cause) {
                        // delete from storage or secondary cache
                        // 删除存储或者二级缓存
                        System.out.printf("testWriter:delete - Key %s was delete (%s)%n", key, graph);
                    }
                })
                // 指定在创建缓存或者最近一次更新缓存后经过固定的时间间隔，刷新缓存
                .refreshAfterWrite(4, TimeUnit.SECONDS)
                .build(k -> createExpensiveGraph(k));

        cache.put(key, "personService.findOne1()");
        cache.invalidate(key);

        System.out.println("第一次获取缓存");
        Object object = cache.get(key);

        System.out.println("等待4.1S后，第二次次获取缓存");
        // 直接指定时钟
        ticker.advance(4100, TimeUnit.MILLISECONDS);
        cache.get(key);

        System.out.println("等待5.1S后，第三次次获取缓存");
        // 直接指定时钟
        ticker.advance(5100, TimeUnit.MILLISECONDS);
        cache.get(key);

        return object;
    }


    public static Object testStatistics() {
        String key = "name1";
        // 用户测试，一个时间源，返回一个时间值，表示从某个固定但任意时间点开始经过的纳秒数。
        FakeTicker ticker = new FakeTicker();

        // 基于固定的到期策略进行退出
        // expireAfterAccess
        LoadingCache<String, Object> cache = Caffeine.newBuilder()
                .removalListener((String k, Object graph, RemovalCause cause) ->
                        System.out.printf("执行移除监听器- Key %s was removed (%s)%n", k, cause))
                .ticker(ticker::read)
                .expireAfterWrite(5, TimeUnit.SECONDS)
                // 开启统计
                .recordStats()
                // 指定在创建缓存或者最近一次更新缓存后经过固定的时间间隔，刷新缓存
                .refreshAfterWrite(4, TimeUnit.SECONDS)
                .build(k -> createExpensiveGraph(k));

        for (int i = 0; i < 10; i++) {
            cache.get(key);
            cache.get(key + i);
        }
        // 驱逐是异步操作，所以这里要手动触发一次回收操作
//        ticker.advance(5100, TimeUnit.MILLISECONDS);
        // 手动触发一次回收操作
//        cache.cleanUp();

        System.out.println("缓存命数量：" + cache.stats().hitCount());
        System.out.println("缓存命中率：" + cache.stats().hitRate());
        System.out.println("缓存丢弃数量：" + cache.stats().evictionCount());
        System.out.println("加载新值所花费的平均时间：" + cache.stats().averageLoadPenalty()/1000/1000+"ms");
        System.out.println("缓存总请求数：" + cache.stats().requestCount());
        System.out.println("远端缓存加载数：" + cache.stats().loadSuccessCount());
        System.out.println("远端缓存加载成功率：" + (1-cache.stats().loadFailureRate()));

        return cache.get(key);
    }


    public static Object testAsync() {
        String key = "name1";
        // 用户测试，一个时间源，返回一个时间值，表示从某个固定但任意时间点开始经过的纳秒数。
        FakeTicker ticker = new FakeTicker();

        ExecutorService executor = Executors.newFixedThreadPool(10);


        // 基于固定的到期策略进行退出
        // expireAfterAccess
        LoadingCache<String, List<Integer>> cache = Caffeine.newBuilder()
                .expireAfterWrite(5, TimeUnit.SECONDS)
                // 开启统计
                .recordStats()
                .build(k->(createCosts(k)));


        for (int i=0;i<10;i++) {
            int finalI = i;
            executor.execute(()->{
                for (int k=0;k<10;k++) {
                    List<Integer> costs = cache.get(String.valueOf(finalI));
                    costs.add(k);
                    cache.put(String.valueOf(finalI),costs);
                }
            });
        }


        try {
            Thread.sleep(500);
        }catch (Exception ex) {

        }
        System.out.println(cache.asMap());

        return null;
    }

}
