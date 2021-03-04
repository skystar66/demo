package com.liveme.demo.hashcrule.consistenthash;

import com.liveme.demo.hashcrule.HashCircle;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class TestHash {


    public static void main(String[] args) {

        AbstractConsistentHash hash = new TreeMapConsistentHash();

        List<String> urls = Arrays.asList("127.0.0.1","127.0.0.2","127.0.0.3","127.0.0.4");


        for (String url : urls) {
            hash.add(hash.hash(url), url);

        }

        long start = System.currentTimeMillis();
        for (int i=0;i<100000;i++) {
            String node = "test_" + i + "node";

            hash.getFirstNodeValue(node);
        }
        System.out.println("第一种 生成5w个URL 耗时："+(System.currentTimeMillis()-start)+"ms");
        HashCircle instance = HashCircle.getInstance();

        instance.init("/chat/cluster","127.0.0.1,127.0.0.2,127.0.0.3,127.0.0.4");

        start = System.currentTimeMillis();
        for(int i=0;i<100000;i++){
            String node = "test_" + i + "node";
            instance.get("/chat/cluster", node);

        }
        System.out.println("第二种 生成5w个URL 耗时："+(System.currentTimeMillis()-start)+"ms");




    }


}
