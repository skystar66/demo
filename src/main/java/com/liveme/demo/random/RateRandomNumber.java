package com.bizseer.hubble.anomaly.utils;

import org.apache.commons.lang3.RandomUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 按几率产生随机数
 * 例如，产生0.1-100的随机数，0.1-1的几率是90%，1-10的几率是9%，10-100的几率是1%
 *
 * @author xuliang
 */
public class RateRandomNumber {


    private static List<Integer> separates = new ArrayList<>();
    private static List<Integer> percents = new ArrayList<>();

    private static int min = -1;
    private static int max = 8;

    static {
        separates.add(2);
        separates.add(5);
        separates.add(7);
        percents.add(0);
        percents.add(80);
        percents.add(20);
        percents.add(0);

    }

    /**
     * 产生随机数
     *
     * @param min 最小值
     * @param max 最大值
     * @return 随机结果
     */
    public static Integer produceRandomNumber(int min, int max) {
        return RandomUtils.nextInt(min, max); //[min,max]
    }


    /**
     * 按比率产生随机数
     *
     * @return 按比率随机结果
     */
    public static int produceRateRandomNumber() {
        return produceRateRandomNumber(min, max, separates, percents);
    }

    /**
     * 按比率产生随机数
     *
     * @param min       最小值
     * @param max       最大值
     * @param separates 分割值（中间插入数）
     * @param percents  每段数值的占比（几率）
     * @return 按比率随机结果
     */
    public static int produceRateRandomNumber(int min, int max,
                                              List<Integer> separates,
                                              List<Integer> percents) {
        if (min > max) {
            throw new IllegalArgumentException("min值必须小于max值");
        }
        if (separates == null || percents == null || separates.size() == 0) {
            return produceRandomNumber(min, max);
        }
        if (separates.size() + 1 != percents.size()) {
            throw new IllegalArgumentException("分割数字的个数加1必须等于百分比个数");
        }
        int totalPercent = 0;
        for (Integer p : percents) {
            if (p < 0 || p > 100) {
                throw new IllegalArgumentException("百分比必须在[0,100]之间");
            }
            totalPercent += p;
        }
        if (totalPercent != 100) {
            throw new IllegalArgumentException("百分比之和必须为100");
        }
        for (double s : separates) {
            if (s <= min || s >= max) {
                throw new IllegalArgumentException("分割数值必须在(min,max)之间");
            }
        }
        int rangeCount = separates.size() + 1; //例如：3个插值，可以将一个数值范围分割成4段
        //构造分割的n段范围
        List<Range> ranges = new ArrayList<>();
        int scopeMax = 0;
        for (int i = 0; i < rangeCount; i++) {
            Range range = new Range();
            range.min = (i == 0 ? min : separates.get(i - 1));
            range.max = (i == rangeCount - 1 ? max : separates.get(i));
            range.percent = percents.get(i);

            //片段占比，转换为[1,100]区间的数字
            range.percentScopeMin = scopeMax + 1;
            range.percentScopeMax = range.percentScopeMin + (range.percent - 1);
            scopeMax = range.percentScopeMax;

            ranges.add(range);
        }
        //结果赋初值
        int r = min;
        int randomInt = RandomUtils.nextInt(1, 101); //[1,100]
        for (int i = 0; i < ranges.size(); i++) {
            Range range = ranges.get(i);
            //判断使用哪个range产生最终的随机数
            if (range.percentScopeMin <= randomInt && randomInt <= range.percentScopeMax) {
                r = produceRandomNumber(range.min, range.max);
                break;
            }
        }
        return r;
    }



    public static class Range {
        public int min;
        public int max;
        public int percent; //百分比

        public int percentScopeMin; //百分比转换为[1,100]的数字的最小值
        public int percentScopeMax; //百分比转换为[1,100]的数字的最大值
    }

    public static void main(String[] args) {


//        maxValue: 1.0
//        minValue: 0.0
//        rateList: 0,80,20,0
//        separates: 0.0,0.5,1.0

//        List<Integer> separates = new ArrayList<Integer>();
//        separates.add(1);
//        separates.add(3);
//        separates.add(5);
//        List<Integer> percents = new ArrayList<Integer>();
//        percents.add(0);
//        percents.add(80);
//        percents.add(20);
//        percents.add(0);
//        percents.add(1);
        long start = System.currentTimeMillis();
        for (int i = 0; i < 1000000; i++) {


            RandomUtils.nextInt(2,6);
//            double number = produceRateRandomNumber();
//            System.out.println(String.format("%.2f", number));
//            System.out.println(number);
        }

        System.out.println("cost:"+(System.currentTimeMillis()-start));
    }
}