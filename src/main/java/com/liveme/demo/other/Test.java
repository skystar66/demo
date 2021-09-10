package com.liveme.demo.other;

import com.google.common.collect.Maps;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.liveme.demo.util.JsonUtil;

import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class Test {


    public static void main(String[] args) {

//
//        Set<String> strings = new HashSet<>();
//
//
//        for (int i = 0; i < 10; i++) {
//
//            Set<String> set = new HashSet<>();
//            set.add(i+"str");
//            set.add(i+"str");
//            set.add(i+"str");
//            set.add(i+"str");
//            set.add(i+"str");
//            set.add(i+"str");
//            strings.addAll(set);
//        }
//
//
//
//        System.out.println(strings);
//
//
//        int count = 32;
//
//
//        System.out.println(count / 3 + 1);
//
//        Map<Integer,String> map = new HashMap<>();
//        map.put(1,"111");
//        map.put(3,"111");
//        map.put(2,"111");
//        map.put(4,"111");
//
//        Integer[] arr = map.keySet().toArray(new Integer[4]);
//        Arrays.sort(arr);
//        for (Integer integer : arr) {
//            System.out.println(integer);
//        }


//        System.out.println(getEndMoney());
//        System.out.println(getEndMoney2());
//
//
//        Integer num1=1290;
//        Integer num2=1290;
//        System.out.println(num1==num2);
//
////        System.out.println(print());
//
//
//        ConcurrentMap<String, ConcurrentHashMap<String, String>> gagMappingMap = new ConcurrentHashMap<String, ConcurrentHashMap<String, String>>();
//        ConcurrentHashMap<String, String> temp = new ConcurrentHashMap<>();
//        temp.put("a","1");
//        temp.put("b","1");
//        temp.put("c","1");
//
//        gagMappingMap.put("1",temp);
//
//        System.out.println(gagMappingMap.get("1").size());
//
//        temp.remove("a");
//        temp.remove("b");
//        temp.remove("c");
//
//
//        System.out.println(gagMappingMap.get("1"));
//
//
//
//        print();
//        print2();
//

//        System.out.println("hello word!!!");

//
//
//        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(30,
//                new ThreadFactory() {
//            private int num = 0;
//            @Override
//            public Thread newThread(Runnable r) {
//                Thread thread = new Thread(r);
//                num ++;
//                thread.setName("room-message-schedule" + num);
//                return thread;
//            }
//        });
//
//        //每200ms 遍历一次队列
//        scheduledExecutorService.scheduleAtFixedRate(new Runnable() {
//            @Override
//            public void run() {
//                System.out.println(Thread.currentThread().getName()+"-----111");
//            }
//        },0,5000, TimeUnit.MILLISECONDS);
//
//        DoubleModel doubleModel = new DoubleModel();
//        double[] doubles= new double[]{1.0,2.0,3.0,4.0};
//        doubleModel.setDoubles(doubles);
//        for (int i=0;i<1000000;i++) {
//            batchTestDouble(doubles);
//        }
//        System.out.println("执行完成");
//
//        System.out.println(System.currentTimeMillis());
//        System.out.println(calDistanceNextExecuteTime(1,1));


//        groupTest();

//        long start = System.currentTimeMillis();
//        for (int i=0;i<1000000;i++) {
//            compareJson();
//        }
//        System.out.println("compareJson cost:"+(System.currentTimeMillis()-start)+"ms");
//
//        start = System.currentTimeMillis();
//        for (int i=0;i<1000000;i++) {
//            compareStringBuilder();
//        }
//        System.out.println("compareStringBuilder cost:"+(System.currentTimeMillis()-start)+"ms");


        ThreadLocal threadLocal = new ThreadLocal();

        threadLocal.set("1");
        System.out.println((byte) 0x04&0xFF);
//        System.out.println(mqpTest());
//        System.out.println(compareStringBuilder());

    }

    public static  Map<String, String> mqpTest(){
        Map<String, String> sceneMap = new HashMap<>();

        sceneMap.getOrDefault("1","1");
        return sceneMap;

    }


    static String str = "{\"viewType\":\"1_2\",\"callSystem\":\"VMIS\",\"conditions\":[{\"classCode\":\"SubSystem\",\"attrs\":[{\"attrCode\":\"en_short_name\",\"attrValue\":\"N-HRFD\"}]},{\"classCode\":\"TranCode\",\"attrs\":[{\"attrCode\":\"service_id\",\"attrValue\":\"A04815228\"}]}]}";

    public static String compareJson() {

        JsonObject data = new JsonObject();
        data.addProperty("viewType", "1_2");
        data.addProperty("callSystem", "VMIS");

        JsonArray conditions = new JsonArray();


        JsonObject j1 = new JsonObject();
        j1.addProperty("classCode", "SubSystem");

        JsonArray appNames = new JsonArray();
        JsonObject appName = new JsonObject();
        appName.addProperty("attrCode", "en_short_name");
        appName.addProperty("attrValue", "N-HRFD");
        appNames.add(appName);
        j1.add("attrs", appNames);


        JsonObject j2 = new JsonObject();
        j2.addProperty("classCode", "TranCode");

        JsonArray tcs = new JsonArray();
        JsonObject tc = new JsonObject();
        tc.addProperty("attrCode", "service_id");
        tc.addProperty("attrValue", "A04815228");
        tcs.add(tc);
        j2.add("attrs", tcs);



        conditions.add(j1);
        conditions.add(j2);

        return JsonUtil.toJson(conditions);
    }


    public static String compareFormat() {

        JsonUtil.fromJson(str);

        JsonObject data = new JsonObject();
        data.addProperty("viewType", "1_2");
        data.addProperty("callSystem", "VMIS");

        JsonArray conditions = new JsonArray();


        JsonObject j1 = new JsonObject();
        j1.addProperty("classCode", "SubSystem");

        JsonArray appNames = new JsonArray();
        JsonObject appName = new JsonObject();
        appName.addProperty("attrCode", "en_short_name");
        appName.addProperty("attrValue", "N-HRFD");
        appNames.add(appName);
        j1.add("attrs", appNames);


        JsonObject j2 = new JsonObject();
        j2.addProperty("classCode", "TranCode");

        JsonArray tcs = new JsonArray();
        JsonObject tc = new JsonObject();
        tc.addProperty("attrCode", "service_id");
        tc.addProperty("attrValue", "A04815228");
        tcs.add(tc);
        j2.add("attrs", tcs);



        conditions.add(j1);
        conditions.add(j2);

        return JsonUtil.toJson(conditions);
    }

    private final static String jsonStart = "{";
    private final static String jsonArrayStart = "[";
    private final static String jsonEnd = "}";
    private final static String jsonArrayEnd = "]";
    private final static String commaMaker = ",";
    private final static String commaColon = ":";
    private final static String doubleQuotationMarks = "\"";

    public static String compareStringBuilder() {

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(jsonStart)
                .append(doubleQuotationMarks).append("viewType").append(doubleQuotationMarks).append(commaColon).append(doubleQuotationMarks).append("1_2").append(doubleQuotationMarks).append(commaMaker)
                .append(doubleQuotationMarks).append("callSystem").append(doubleQuotationMarks).append(commaColon).append(doubleQuotationMarks).append("VMIS").append(doubleQuotationMarks).append(commaMaker)
                .append(doubleQuotationMarks).append("conditions").append(doubleQuotationMarks).append(commaColon).append(jsonArrayStart)
                .append(jsonStart)
                .append(doubleQuotationMarks).append("classCode").append(doubleQuotationMarks).append(commaColon).append(doubleQuotationMarks).append("SubSystem").append(doubleQuotationMarks).append(commaMaker)
                .append(doubleQuotationMarks).append("attrs").append(doubleQuotationMarks).append(commaColon).append(jsonArrayStart)
                .append(jsonStart)
                .append(doubleQuotationMarks).append("attrCode").append(doubleQuotationMarks).append(commaColon).append(doubleQuotationMarks).append("en_short_name").append(doubleQuotationMarks).append(commaMaker)
                .append(doubleQuotationMarks).append("attrValue").append(doubleQuotationMarks).append(commaColon).append(doubleQuotationMarks).append("N-HRFD").append(doubleQuotationMarks)
                .append(jsonEnd)
                .append(jsonArrayEnd)
                .append(jsonEnd).append(commaMaker)
                .append(jsonStart)
                .append(doubleQuotationMarks).append("classCode").append(doubleQuotationMarks).append(commaColon).append(doubleQuotationMarks).append("SubSystem").append(doubleQuotationMarks).append(commaMaker)
                .append(doubleQuotationMarks).append("attrs").append(doubleQuotationMarks).append(commaColon).append(jsonArrayStart)
                .append(jsonStart)
                .append(doubleQuotationMarks).append("attrCode").append(doubleQuotationMarks).append(commaColon).append(doubleQuotationMarks).append("en_short_name").append(doubleQuotationMarks).append(commaMaker)
                .append(doubleQuotationMarks).append("attrValue").append(doubleQuotationMarks).append(commaColon).append(doubleQuotationMarks).append("N-HRFD").append(doubleQuotationMarks)
                .append(jsonEnd)
                .append(jsonArrayEnd)
                .append(jsonEnd)
                .append(jsonArrayEnd).append(jsonEnd);
        return stringBuilder.toString();
    }


//    public static void groupTest() {
//
//        List<A> as = new ArrayList<>();
//
//
//        List<B> bs11 = new ArrayList<>();
//
//        bs11.add(new B(2l));
//        bs11.add(new B(6l));
//        bs11.add(new B(1l));
//
//
//        List<B> bs2 = new ArrayList<>();
//
//
//        bs2.add(new B(0l));
//        bs2.add(new B(10l));
//        bs2.add(new B(1l));
//
//
//        List<B> bs3 = new ArrayList<>();
//        bs3.add(new B(9l));
//        bs3.add(new B(5l));
//        bs3.add(new B(12l));
//
//        A a = new A("1", bs11);
//        A a1 = new A("1", bs1);
//        A a2 = new A("2", bs2);
//        A a3 = new A("3", bs3);
//
//        as.add(a);
//        as.add(a1);
//        as.add(a2);
//        as.add(a3);
//
//
//        Map<String, List<A>> sceneMap = as.stream().collect(Collectors.groupingBy(A::getName
//        ));
//        sceneMap.values().stream().forEach(b -> {
//
//            b.stream().forEach(b2 -> {
//
//                Collections.sort(b2.getBs(), new Comparator<B>() {
//                    @Override
//                    public int compare(B o1, B o2) {
//
//
//                        return o2.getCount().compareTo(o1.getCount());
//                    }
//                });
//            });
//
//        });
//
//
//        List<Map.Entry<String, List<A>>> list = new ArrayList<>(sceneMap.entrySet());
//
//
//        Collections.sort(list, new Comparator<Map.Entry<String, List<A>>>() {
//            @Override
//            public int compare(Map.Entry<String, List<A>> o1,
//                               Map.Entry<String, List<A>> o2) {
//                Long count1 = o1.getValue().stream().mapToLong(A::getSumErrorNums).sum();
//                Long count2 = o2.getValue().stream().mapToLong(A::getSumErrorNums).sum();
//                return count2.compareTo(count1);
//            }
//        });
//
//
//        Map<String, List<A>> sceneMapNew = new HashMap<>();
//
//
//        for (Map.Entry<String, List<A>> entry : sceneMapNew.entrySet()) {
//
//            System.out.println("key:" + entry.getKey());
//
//        }
//
//        System.out.println(sceneMap);
//
//
//    }

    // Map的value值降序排序
    public static <K, V extends Comparable<? super V>> Map<K, V> sortDescend(Map<K, V> map) {
        List<Map.Entry<K, V>> list = new ArrayList<>(map.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<K, V>>() {
            @Override
            public int compare(Map.Entry<K, V> o1, Map.Entry<K, V> o2) {
                int compare = (o1.getValue()).compareTo(o2.getValue());
                return -compare;
            }
        });

        Map<K, V> returnMap = new LinkedHashMap<K, V>();
        for (Map.Entry<K, V> entry : list) {
            returnMap.put(entry.getKey(), entry.getValue());
        }
        return returnMap;
    }


    /**
     * Sort map by value
     *
     * @param map    map source
     * @param isDesc 是否降序，true：降序，false：升序
     * @param limit  取前几条
     * @return 已排序map
     */
    public static <K, V extends Comparable<? super V>> Map<K, V> sortByValue(Map<K, V> map, boolean isDesc, int limit) {
        Map<K, V> result = Maps.newLinkedHashMap();
        if (isDesc) {
            map.entrySet().stream().sorted(Map.Entry.<K, V>comparingByValue().reversed()).limit(limit)
                    .forEach(e -> result.put(e.getKey(), e.getValue()));
        } else {
            map.entrySet().stream().sorted(Map.Entry.<K, V>comparingByValue())
                    .forEachOrdered(e -> result.put(e.getKey(), e.getValue()));
        }
        return result;
    }


    public static <T> Collector<T, ?, List<T>> toSortedList(Comparator<? super T> c) {
        return Collectors.collectingAndThen(Collectors.toCollection(() -> new TreeSet<>(c)), ArrayList::new);
    }

    /**
     * 计算下一次任务执行的时间点
     *
     * @param
     * @return: long
     * @author: xl
     * @date: 2021/6/28
     **/
    public static long calDistanceNextExecuteTime(int CYCLE_BUCKET_NUM, int BUCKET_TIME) {
        long cycleTime = CYCLE_BUCKET_NUM * BUCKET_TIME * 1000;
        long now = System.currentTimeMillis();
        return (now - now % cycleTime) + cycleTime * 2;
    }


    public static void batchTestDouble(double[] nums) {
        double num = nums[0];
    }


    public static void batchTestDouble(DoubleModel nums) {
        double num = nums.getDoubles()[0];
    }


    public static void print() {

        System.out.println("我是学生1");

    }


    public static void print2() {
        System.out.println("我是学生2");
    }


    public static double getEndMoney() {

        double day = 21.75;
        double hour = 8;//每天时长
        double month = 12;//月数
        //总时长
        double sumHour = day * hour * month;// 全年总工作时长
        double workerDay = day * 4;//已工作天数
        double workerHour = hour * workerDay;//已工作时长
        return workerHour / sumHour * 38000 * 2.5;//工作时长/全年总时长*月薪*系数


    }

    public static double getEndMoney2() {


        LinkedBlockingQueue linkedBlockingQueue = new LinkedBlockingQueue();
//        linkedBlockingQueue

        double avgMonth = 38000 / 12;

        return avgMonth * 4 * 2.5;


    }


    public static void testDouble(double[] num) {

        num[0] = 100;


    }


}
