package com.liveme.demo.single;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SingleTest {


    private static Map<String,SessionDao> sessionDaoMap = new ConcurrentHashMap<>();


    public static void main(String[] args) {



        for (int i=0;i<2;i++) {


            SessionDao sessionDao = SessionDao.getInstance();

            sessionDao.initDataSource(i+"1");

            sessionDaoMap.put(i+"",sessionDao);

        }


        for (Map.Entry<String, SessionDao> stringSessionDaoEntry : sessionDaoMap.entrySet()) {
            System.out.println(stringSessionDaoEntry.getValue().getValue());
        }
        System.out.println(sessionDaoMap);



    }


}
