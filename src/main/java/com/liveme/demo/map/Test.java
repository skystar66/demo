package com.liveme.demo.map;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLongArray;
import java.util.stream.Collectors;

public class Test {


    static int cycleBucketNum = 10;

    static int cycleNum = 2;


    static int bucketSize = cycleBucketNum * cycleNum;

    static AtomicLongArray atomicLongArray = new AtomicLongArray(cycleBucketNum);


    public static void main(String[] args) {

//        Map<Integer,String> map = new HashMap<>();
//        int page = 1;
//        map.put(page,"ss");
//        System.out.println(map.get(page));

        List<String> list = new ArrayList<>();
        list.add("1");
        list.add("2");
        list.add("3");
        list.add("4");


        list = list.stream().filter(s -> !s.equals("1")).collect(Collectors.toList());

        System.out.println(list.toString());


        AtomicLongArray atomicLongArray = new AtomicLongArray(10);

        int index = 6;


        System.out.println(atomicLongArray.incrementAndGet(index));
        System.out.println(atomicLongArray.incrementAndGet(index));
//
//        int bucketIndex = 0;
//
//
//
//        for (int i=0;i<10;i++) {
//            sliding(i);
//        }


        System.out.println(2/10);


    }


    public static void sliding(int bucketIndex) {
        long curSecondValue = atomicLongArray.incrementAndGet(bucketIndex);
        long slidingIndex = curSecondValue;
        for (int i = bucketIndex - cycleBucketNum + 1; i < bucketIndex; i++) {
            slidingIndex += atomicLongArray.get(switchIndex(i));
        }
        System.out.println("curSecondValue:" + curSecondValue + ",slidingCycleValue:" + slidingIndex);

    }

    /**
     * 由于需要把桶（秒）的数组打造成环形数组，所以这里需要对index做特殊处理
     *
     * @param index
     * @return
     */
    public static int switchIndex(int index) {
        if (index >= 0 && index < bucketSize) {
            return index;

        } else if (index < 0) {
            return bucketSize + index;

        } else {
            return index - bucketSize;
        }
    }


}
