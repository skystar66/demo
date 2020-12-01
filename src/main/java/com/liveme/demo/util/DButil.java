package com.liveme.demo.util;

import com.mongodb.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class DButil {



    //在建立（打开）套接字连接时的超时时间（ms），默认为0（无限）
    public static int connectTimeout = 100000;       //TODO     12000     导redis cluster数据，拉长时间

    //套接字超时时间;该值会被传递给Socket.setSoTimeout(int)。默认为0（无限）
    private static int socketTimeout = 100000;          //TODO     10000

    //被阻塞线程从连接池获取连接的最长等待时间（ms）
    private static int maxWaitTime = 20000;


    private static ConcurrentMap<String, DB> map = new ConcurrentHashMap<String, DB>();

    public static void init() {


        MongoClientOptions mco = new MongoClientOptions.Builder()
                // .autoConnectRetry(autoConnectRetry)
                .writeConcern(WriteConcern.SAFE).connectionsPerHost(500).threadsAllowedToBlockForConnectionMultiplier(20)
                .connectTimeout(connectTimeout).socketTimeout(socketTimeout).maxWaitTime(maxWaitTime).build();

        ServerAddress serverAddress = new ServerAddress("34.236.78.204", 27018);

        MongoClient mongoClient = new MongoClient(serverAddress,mco);

        DB db = mongoClient.getDB("chatmsg");

        map.putIfAbsent("chat",db);

        DB session = mongoClient.getDB("session");

        map.putIfAbsent("session",session);
    }

    public static ConcurrentMap<String, DB> getMap() {
        return map;
    }
}
