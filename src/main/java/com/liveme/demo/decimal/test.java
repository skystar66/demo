package com.liveme.demo.decimal;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;

public class test {


    public static void main(String[] args) {

//        testBigDecimal();
//        testDecimalFormat();
//        testNumberFormat();
//        testStringFormat();

        System.out.println(produceBoundaryKey("123",1,false));

//        double a = 37065.16673900815;
//
//        NumberFormat numberFormat = NumberFormat.getNumberInstance();
//        numberFormat.setMaximumFractionDigits(4);
//        numberFormat.setRoundingMode(RoundingMode.HALF_UP);
//        numberFormat.setGroupingUsed(false);
//        String num = numberFormat.format(a);
//
//        System.out.println(Double.parseDouble(num));
    }

    public static String produceBoundaryKey(String modelId, int pageNumber, boolean isDiff) {
        return (isDiff ? "df:" : "") + modelId + ":" + pageNumber;
    }


    static void testNumberFormat() {
        long start = System.currentTimeMillis();
        double a = 123.56789;
        NumberFormat numberFormat = NumberFormat.getNumberInstance();
        numberFormat.setMaximumFractionDigits(4);
        numberFormat.setRoundingMode(RoundingMode.HALF_UP);
        numberFormat.setGroupingUsed(false);
        for (int i = 0; i < 100000; i ++) {
            double aaa = Double.parseDouble(numberFormat.format(a));
//            System.out.println(aaa);
        }
        long end = System.currentTimeMillis();
        System.out.println("100000 次 numberFormat cost time: " + (end - start)+"ms");
    }

    static void testDecimalFormat() {
        long start = System.currentTimeMillis();
        double a = 123.56789;
        DecimalFormat df = new DecimalFormat("#.######");
        df.setRoundingMode(RoundingMode.HALF_UP);
        for (int i = 0; i < 100000; i ++) {
            double aaa = Double.parseDouble(df.format(a));
        }
        long end = System.currentTimeMillis();
        System.out.println("100000 次 decimalFormat cost time: " + (end - start)+"ms");
    }

    static void testBigDecimal() {
        long start = System.currentTimeMillis();
        double a = 123.56789;
        for (int i = 0; i < 100000; i ++) {
            double b = new BigDecimal(a).setScale(6, RoundingMode.HALF_UP).doubleValue();
        }
        long end = System.currentTimeMillis();
        System.out.println("100000 次 bigDecimal cost time: " + (end - start)+"ms");
    }

    static void testStringFormat() {
        long start = System.currentTimeMillis();
        double a = 123.56789;
        for (int i = 0; i < 100000; i ++) {
            double aaa = Double.parseDouble(String.format("%.4f", a));
//            System.out.println(aaa);
        }
        long end = System.currentTimeMillis();
        System.out.println("100000 次 string.format(%.6f) cost time: " + (end - start)+"ms");
    }

}

