package com.liveme.demo.es;

import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EsTest {


    public static void main(String[] args) {

        String modelId= "sys:0";
        String modelId2="sys";

        if (modelId2.equals(modelId.substring(0,modelId.lastIndexOf(":")))) {

            System.out.println("true");
        }


    }



}

