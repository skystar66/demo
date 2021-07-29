//package com.liveme.demo.redis;
//
//import com.esotericsoftware.kryo.Kryo;
//import com.esotericsoftware.kryo.io.Input;
//import com.esotericsoftware.kryo.io.Output;
//import com.fasterxml.jackson.core.type.TypeReference;
//import com.liveme.demo.msgqueue.monitor.ThreadPoolMonitor;
//import com.liveme.demo.redis.point.Point;
//import com.liveme.demo.util.FastJsonUtil;
//import com.liveme.demo.util.JsonUtil;
//import com.liveme.demo.util.KryoUtil;
//import org.apache.flink.api.common.typeinfo.TypeInformation;
//import org.apache.flink.api.common.typeutils.TypeSerializer;
//import org.apache.flink.api.java.typeutils.runtime.kryo.KryoSerializer;
//import org.apache.storm.serialization.DefaultKryoFactory;
//import org.apache.storm.serialization.IKryoFactory;
//import org.apache.storm.serialization.ITupleSerializer;
//import org.apache.storm.serialization.KryoTupleSerializer;
//import org.javatuples.Sextet;
//import org.javatuples.Tuple;
//
//import java.io.ByteArrayInputStream;
//import java.io.ByteArrayOutputStream;
//import java.io.Closeable;
//import java.util.*;
//import java.util.concurrent.*;
//
//public class RedisTest {
//
//
//    private static ExecutorService executorAsyncLoadCachePool =
//            Executors.newFixedThreadPool(500);
//
//
//    public static void main(String[] args) {
//
//
//        RedisAPI.initPool("127.0.0.1", 6379, 10, 10, 100000);
//
//
//        String key = "test-hash";
//
//        Sextet<Double, Integer, Long, Double, Integer, Long> sextet = new Sextet<>(1.0, 1, 1l, 1.0, 1, 1l);
//        TypeInformation<Sextet> info = TypeInformation.of(Sextet.class);
//
//
//
//
//
//
//        TypeSerializer typeSerializer = new KryoSerializer(Tuple.class,null);
//
//        typeSerializer.get
//        RedisAPI.hset(key.getBytes(), "d1".getBytes(), KryoUtil.writeObjectToByteArray( sextet));
//        byte[] b1 = RedisAPI.hget(key.getBytes(), "d1".getBytes());
//
//        System.out.println(KryoUtil.readFromByteArray( b1, Sextet.class));
//
//
////
////        double[] d1 = new double[]{1.2,2.3,3.5};
////
////
////       byte[] b1 =  RedisAPI.hget(key.getBytes(),"d1".getBytes());
////
////
////        System.out.println(KryoUtil.readFromByteArray(b1,double[].class)[0]);
//
//    }
//
//
//    /**
//     * 使用ThreadLocal创建Kryo
//     * 把java对象序列化成byte[];
//     *
//     * @param obj java对象
//     * @return
//     */
//    public static <T> byte[] writeObjectToByteArray(Kryo kryo, T obj) {
//        ByteArrayOutputStream os = null;
//        Output output = null;
//        if (null != obj) {
//            try {
//                os = new ByteArrayOutputStream();
//                output = new Output(os);
//                kryo.writeObject(output, obj);
//                close(output);
//                return os.toByteArray();
//            } catch (Exception e) {
//                e.printStackTrace();
//            } finally {
//                close(os);
//
//            }
//        }
//        return null;
//    }
//
//
//    /**
//     * 使用ThreadLocal创建Kryo
//     * 把byte[]反序列化成指定的java对象
//     *
//     * @param bytes
//     * @param t     指定的java对象
//     * @param <T>
//     * @return 指定的java对象
//     */
//    public static <T> T readFromByteArray(Kryo kryo, byte[] bytes, Class<T> t) {
//        ByteArrayInputStream is = null;
//        Input input = null;
//        if (null != bytes && bytes.length > 0 && null != t) {
//            try {
//                is = new ByteArrayInputStream(bytes);
//                input = new Input(is);
//                return kryo.readObject(input, t);
//            } catch (Exception e) {
//                e.printStackTrace();
//            } finally {
//                close(is);
//                close(input);
//            }
//        }
//        return null;
//    }
//
//    /**
//     * 关闭io流对象
//     *
//     * @param closeable
//     */
//    public static void close(Closeable closeable) {
//        if (closeable != null) {
//            try {
//                closeable.close();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//    }
//
//
//    public static void delBatch() {
//    }
//
//
////    private static long removeBatchBoundaryNearbyRefTrendPatchInRedis(SeasonMedianFieldCacheTypeEnum typeEnum, byte[]... key) {
////        return RedisAPI.hdel(getBoundaryNearbyRefTrendPatchKeyPrefix(typeEnum).getBytes(), key);
////    }
//
//
//    public static void ser() {
//
//
//        List<Point> points = new ArrayList<>();
//
//
//        Point point = new Point();
//
//        point.setTimestamp(System.currentTimeMillis());
//
//        point.setValue(1.5);
//
//
//        Point point1 = new Point();
//
//        point1.setTimestamp(System.currentTimeMillis());
//
//        point1.setValue(2.5);
//
//        points.add(point);
//        points.add(point1);
//
//
//        String key = "test-kryo";
//
//        String filed = "kyro-fil";
//
//
//        List<double[]> doubles = new ArrayList<>();
//
//        double[] d1 = new double[]{1.1, 2.3, 4.6};
//        double[] d12 = new double[]{2.1, 3.3, 5.6};
//        doubles.add(null);
//
//        doubles.add(null);
//        doubles.add(null);
//        RedisAPI.hset(key.getBytes(), filed.getBytes(), KryoUtil.writeObjectToByteArray(doubles));
//
//
////        RedisAPI.hset(key.getBytes(),filed.getBytes(),KryoUtil.writeObjectToByteArray(points));
//
//
//        byte[] bytes = RedisAPI.hget(key.getBytes(), filed.getBytes());
//
//        KryoUtil.readFromByteArray(bytes, ArrayList.class);
////        String strCache = new String(bytes);
//        List<double[]> pointSet = KryoUtil.readFromByteArray(RedisAPI.hget(key.getBytes(), filed.getBytes()), ArrayList.class);
//
//
//        for (int i = 0; i < pointSet.size(); i++) {
//            System.out.println(pointSet.get(i));
//
//        }
//
//
//        ExecutorService executor = Executors.newFixedThreadPool(10);
//
//        int count = 0;
//        for (int i = 0; i < 2000; i++) {
//
//            count++;
//            int finalCount = count;
//            if (count > 5) {
//                count = 0;
//            }
//            int finalI = i;
//            executor.execute(() -> {
//
//
//                System.out.println("finalI:" + finalI);
//
//                new Thread(new Runnable() {
//                    @Override
//                    public void run() {
//                        try {
//                            if (finalCount == 5) {
//                                Thread.sleep(1000);
//                            }
//                            System.out.println("第二次执行：" + finalI);
//                        } catch (Exception ex) {
//
//                        }
//                    }
//                }).start();
//
//
////
////
////
//
//            });
//
//
//        }
//
//
////        for (double[] point2 : pointSet) {
////
////
////
////        }
//
//        System.out.println("redis return set point:" + pointSet);
//
//
//    }
//
//
//    public static void serFastJson() {
//
//
//        List<Point> points = new ArrayList<>();
//
//
//        Point point = new Point();
//
//        point.setTimestamp(System.currentTimeMillis());
//
//        point.setValue(1.5);
//
//
//        Point point1 = new Point();
//
//        point1.setTimestamp(System.currentTimeMillis());
//
//        point1.setValue(2.5);
//
//        points.add(point);
//        points.add(point1);
//
//
//        String key = "test-kryo";
//
//        String filed = "kyro-fil";
//
//
//        List<double[]> doubles = new ArrayList<>();
//
//        double[] d1 = new double[]{1.1, 2.3, 4.6};
//        double[] d12 = new double[]{2.1, 3.3, 5.6};
//        doubles.add(d1);
//
//        doubles.add(d12);
//        RedisAPI.hset(key.getBytes(), filed.getBytes(), FastJsonUtil.FastJsonObjectToBytes(doubles));
//
//
////        RedisAPI.hset(key.getBytes(),filed.getBytes(),KryoUtil.writeObjectToByteArray(points));
//
//
//        byte[] bytes = RedisAPI.hget(key.getBytes(), filed.getBytes());
//
//        FastJsonUtil.FastJsonBytesToObject(bytes, ArrayList.class);
////        String strCache = new String(bytes);
//        List<double[]> pointSet = FastJsonUtil.FastJsonBytesToObject(RedisAPI.hget(key.getBytes(), filed.getBytes())
//                , ArrayList.class);
//
//
//        for (double[] point2 : pointSet) {
//            System.out.println(point2);
//        }
//
//        System.out.println("redis return set point:" + pointSet);
//
//
//    }
//
//
//    public static void testhash() {
//
//
//        List<Point> points = new ArrayList<>();
//
//
//        Point point = new Point();
//
//        point.setTimestamp(System.currentTimeMillis());
//
//        point.setValue(1.5);
//
//
//        Point point1 = new Point();
//
//        point1.setTimestamp(System.currentTimeMillis());
//
//        point1.setValue(2.5);
//
//        points.add(point);
//        points.add(point1);
//
//
//        String key2 = "test-hash";
//
//        //todo 做个byte的测试xs
//
//        RedisAPI.hset(key2.getBytes(), "1".getBytes(), KryoUtil.writeObjectToByteArray(points));
//        RedisAPI.hset(key2.getBytes(), "2".getBytes(), KryoUtil.writeObjectToByteArray(points));
//        RedisAPI.hset(key2.getBytes(), "3".getBytes(), KryoUtil.writeObjectToByteArray(points));
//        RedisAPI.hset(key2.getBytes(), "4".getBytes(), KryoUtil.writeObjectToByteArray(points));
//        RedisAPI.hset(key2.getBytes(), "5".getBytes(), KryoUtil.writeObjectToByteArray(points));
//
//
//        //todo  删除
//        byte[][] bytes1 = new byte[3][];
//        bytes1[0] = "1".getBytes();
//        bytes1[1] = "2".getBytes();
//        bytes1[2] = "3".getBytes();
////        String[] delKeys = new String[10];
//
//        RedisAPI.hdel(key2.getBytes(), bytes1);
//
//
//    }
//
//
//    private static void add(int num) {
//
//        for (int i = 0; i < num; i++) {
//
//            int finalI = i;
//            executorAsyncLoadCachePool.execute(new Runnable() {
//                @Override
//                public void run() {
//                    try {
//                        Thread.sleep(2000);
//
//                        System.out.println(finalI);
//
//
////                        System.out.println("1111");
//                    } catch (Exception ex) {
////                        log.error("takeQueue Async Page 发生异常：{}", ex);
//
//                    }
//                }
//            });
//        }
//    }
//
//
//}
