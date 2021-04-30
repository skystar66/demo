package com.liveme.demo.redis;

import com.liveme.demo.msgqueue.monitor.ThreadPoolMonitor;
import com.liveme.demo.redis.point.Point;
import com.liveme.demo.util.FastJsonUtil;
import com.liveme.demo.util.KryoUtil;

import java.util.*;
import java.util.concurrent.*;

public class RedisTest {




    private static ThreadPoolExecutor executorAsyncLoadCachePool =
            new ThreadPoolMonitor(500,
                    1000,
                    0L,
                    TimeUnit.MILLISECONDS,
                    new LinkedBlockingQueue<>(10 * 10000), "pool-asyncLoadCache-thread");


    public static void main(String[] args) {

        RedisAPI.initPool("10.0.80.222",6379,10,10,100000);


//        Map<String,String> map = new HashMap<>();
//
//        map.put("1","12348");
//        map.put("2","12345");
//        map.put("3","12346");
//        map.put("4","12347");


        String key = "test-hash";
//        RedisAPI.hset(key,"1","12345");
//        RedisAPI.hset(key,"2","12348453");
//        RedisAPI.hset(key,"3","123fsdf48");
//        RedisAPI.hset(key,"4","1234zczx8");
//        RedisAPI.hset(key,"5","1234dsad8");


        String[] delkeys = new String[5];
        delkeys[0]="1";
        delkeys[1]="2";
        delkeys[2]="3";
        delkeys[3]="4";
        delkeys[4]="5";

        RedisAPI.hdel(key,delkeys);


        String[] delKeys = new String[11];
        delKeys[0] = "1";
        for (int i=0;i<10;i++) {
//                    if (count > 10) {
//                        log.warn("循环删除redis中4页前的boundary数据次数过久,次数:{},cursorRedisKey:{}", count, cursorRedisKey);
//                    }
//            log.info("删除redis中的数据，key：{} modelId:{} deletePageCursor:{} 次数:{}",
//                    cursorRedisKey, modelId, deletePageCursor, i+1);
//            cursorRedisKey = RedisWithoutLocalCacheUtil
//                    .produceKeyCommonForBoundaryNearbyRefTrendPatch(modelId, deletePageCursor, isDiff, typeEnum);
//            if (count == deletePageCursor) {
//                continue;
//            }
            delKeys[i+1] = i+"";
        }
//        System.out.println(delKeys);

        ser();



       double[] nums = new double[]{};

        System.out.println(nums.length);

//        testhash();
//        serFastJson();


//
//        int sum =0;
//        for (int i=0;i<10;i++) {
//
//            sum=sum+i;
//
//
//        }
//
//
//        System.out.println(sum
//        );
//





//        add(1);
//        add(2);
//        add(3);
//        add(4);

    }



    public static void ser(){


        List<Point> points = new ArrayList<>();


        Point point = new Point();

        point.setTimestamp(System.currentTimeMillis());

        point.setValue(1.5);


        Point point1 = new Point();

        point1.setTimestamp(System.currentTimeMillis());

        point1.setValue(2.5);

        points.add(point);
        points.add(point1);


        String key = "test-kryo";

        String filed = "kyro-fil";



        List<double[]> doubles = new ArrayList<>();

        double[] d1 = new double[]{1.1,2.3,4.6};
        double[] d12 = new double[]{2.1,3.3,5.6};
        doubles.add(null);

        doubles.add(null);
        doubles.add(null);
        RedisAPI.hset(key.getBytes(),filed.getBytes(),KryoUtil.writeObjectToByteArray(doubles));



//        RedisAPI.hset(key.getBytes(),filed.getBytes(),KryoUtil.writeObjectToByteArray(points));


        byte[] bytes = RedisAPI.hget(key.getBytes(),filed.getBytes());

        KryoUtil.readFromByteArray(bytes,ArrayList.class);
//        String strCache = new String(bytes);
        List<double[]> pointSet = KryoUtil.readFromByteArray(RedisAPI.hget(key.getBytes(),filed.getBytes()),ArrayList.class);




        for (int i=0;i<pointSet.size();i++) {
            System.out.println(pointSet.get(i));

        }


        ExecutorService executor = Executors.newFixedThreadPool(10);

        int count =0;
        for (int i=0;i<2000;i++) {

            count++;
            int finalCount = count;
            if (count>5) {
                count=0;
            }
            int finalI = i;
            executor.execute(()->{


                System.out.println("finalI:"+finalI);

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            if (finalCount == 5) {
                                Thread.sleep(1000);
                            }
                            System.out.println("第二次执行："+finalI);
                        }catch (Exception ex) {

                        }
                    }
                }).start();


//
//
//

            });



        }




//        for (double[] point2 : pointSet) {
//
//
//
//        }

        System.out.println("redis return set point:"+pointSet);






    }


    public static void serFastJson(){


        List<Point> points = new ArrayList<>();


        Point point = new Point();

        point.setTimestamp(System.currentTimeMillis());

        point.setValue(1.5);


        Point point1 = new Point();

        point1.setTimestamp(System.currentTimeMillis());

        point1.setValue(2.5);

        points.add(point);
        points.add(point1);


        String key = "test-kryo";

        String filed = "kyro-fil";



        List<double[]> doubles = new ArrayList<>();

        double[] d1 = new double[]{1.1,2.3,4.6};
        double[] d12 = new double[]{2.1,3.3,5.6};
        doubles.add(d1);

        doubles.add(d12);
        RedisAPI.hset(key.getBytes(),filed.getBytes(), FastJsonUtil.FastJsonObjectToBytes(doubles));



//        RedisAPI.hset(key.getBytes(),filed.getBytes(),KryoUtil.writeObjectToByteArray(points));


        byte[] bytes = RedisAPI.hget(key.getBytes(),filed.getBytes());

        FastJsonUtil.FastJsonBytesToObject(bytes,ArrayList.class);
//        String strCache = new String(bytes);
        List<double[]> pointSet = FastJsonUtil.FastJsonBytesToObject(RedisAPI.hget(key.getBytes(),filed.getBytes())
                ,ArrayList.class);


        for (double[] point2 : pointSet) {
            System.out.println(point2);
        }

        System.out.println("redis return set point:"+pointSet);






    }


    public static void testhash(){



        List<Point> points = new ArrayList<>();


        Point point = new Point();

        point.setTimestamp(System.currentTimeMillis());

        point.setValue(1.5);


        Point point1 = new Point();

        point1.setTimestamp(System.currentTimeMillis());

        point1.setValue(2.5);

        points.add(point);
        points.add(point1);


        String key2 = "test-hash";

        //todo 做个byte的测试xs

        RedisAPI.hset(key2.getBytes(),"1".getBytes(),KryoUtil.writeObjectToByteArray(points));
        RedisAPI.hset(key2.getBytes(),"2".getBytes(),KryoUtil.writeObjectToByteArray(points));
        RedisAPI.hset(key2.getBytes(),"3".getBytes(),KryoUtil.writeObjectToByteArray(points));
        RedisAPI.hset(key2.getBytes(),"4".getBytes(),KryoUtil.writeObjectToByteArray(points));
        RedisAPI.hset(key2.getBytes(),"5".getBytes(),KryoUtil.writeObjectToByteArray(points));



        //todo  删除
        byte[][] bytes1 = new byte[3][];
        bytes1[0]="1".getBytes();
        bytes1[1]="2".getBytes();
        bytes1[2]="3".getBytes();
//        String[] delKeys = new String[10];

        RedisAPI.hdel(key2.getBytes(),bytes1);




    }



    private static void add(int num){

        for (int i=0;i<num;i++) {

            int finalI = i;
            executorAsyncLoadCachePool.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(2000);

                        System.out.println(finalI);


//                        System.out.println("1111");
                    } catch (Exception ex) {
//                        log.error("takeQueue Async Page 发生异常：{}", ex);

                    }
                }
            });
        }
    }


}
