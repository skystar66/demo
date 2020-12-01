package com.liveme.demo.other;

public class Test {


    public static void main(String[] args) {
        System.out.println(getEndMoney());
        System.out.println(getEndMoney2());
    }



    public static double getEndMoney(){

        double day = 21.75;
        double hour = 8;
        double month = 12;
        //总时长
        double sumHour = day*hour*month;
        double workerDay = day*4;
        double workerHour = hour*workerDay;
        return workerHour/sumHour*38000*3;



    }

    public static double getEndMoney2(){

        double avgMonth = 38000/12;

        return avgMonth*4*3;


    }


}
