//package com.liveme.demo.split;
//
//import javafx.util.Pair;
//import org.apache.commons.lang3.StringUtils;
//
//import java.util.HashMap;
//import java.util.Map;
//import java.util.StringTokenizer;
//
//public class Test {
//
//
//    public static void main(String[] args) {
//
//
//        double[] doubles = new double[2];
//        doubles[0]=0.999;
//        doubles[1]=1.001;
//
//        System.out.println("第一种："+doubles.toString().getBytes().length);
//
//        Pair<Double,Double> pair = new Pair<>(0.999, 1.001);
//
//        System.out.println("第二种："+pair.toString().getBytes().length);
//
//
//
////        double[] numd = new double[3];
////        double[] numd1 = new double[numd.length+1];
////        System.out.println(numd1.length);
////
////        numd1=numd;
////
////        numd1[3]=1;
////        System.out.println(numd1.length);
//
//
//
// //
////        int N = 10000;
////
////
////        StringBuilder sb = new StringBuilder();
////        for (int i = 0; i < N; i++) {
////            sb.append("0.0 ");
////        }
////        String s = sb.toString();
////        long startTime = System.currentTimeMillis(); //获取开始时间
////        for (int i = 0; i < 1000; i++) {
////            String[] cS = s.split(" ");
////            for (String s1 : cS) {
////
////            }
////        }
////        long endTime = System.currentTimeMillis(); //获取结束时间
////        System.out.println("split耗时:" + (endTime - startTime) + "ms");
////        long startTime1 = System.currentTimeMillis();
////        for (int i = 0; i < 1000; i++) {
////            String[] cS1 =  StringUtils.split(s," ");
////            for (String s1 : cS1) {
////
////            }
////        }
////        long endTime1 = System.currentTimeMillis();
////        System.out.println("StringUtils split耗时:" + (endTime1 - startTime1) + "ms");
////
////
////        long startTime2 = System.currentTimeMillis();
////        for (int i = 0; i < 1000; i++) {
//////            String[] cS1 =  StringUtils.split(s," ");
////            StringTokenizer stringTokenizer = new StringTokenizer(s,"");
////            while (stringTokenizer.hasMoreTokens()) {
////                System.out.println(stringTokenizer.nextToken());
////            }
////
////        }
////        long endTime2 = System.currentTimeMillis();
////
////
////
////        System.out.println("StringTokenizer split耗时:" + (endTime2 - startTime2) + "ms");
////
////
////
//
//
//
//
//    }
//
//
//
//
//
//
//}
