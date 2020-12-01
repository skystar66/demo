package com.liveme.demo.page;

import org.springframework.util.CollectionUtils;

import java.util.*;

public class TestPage {


    private static List<String> numLists = new ArrayList<>();


    private static int pageSize = 2;

    private static int count = 0;

    public static void main(String[] args) {

        numLists.add("1");
        numLists.add("2");
        numLists.add("3");
        numLists.add("4");
        numLists.add("5");
        numLists.add("6");
        numLists.add("7");
        numLists.add("8");
        numLists.add("9");
        numLists.add("10");

        List<String> numLists2 = new ArrayList<>();
        System.out.println(getRedisGagUserList(0, numLists2));

    }


    /**
     * 递归获取Redis禁言列表
     */
    public static List<String> getRedisGagUserList(int start, List<String> gagUids) {
        int end = start + pageSize;
        List<String> stringList = getData(start, end);
        if (CollectionUtils.isEmpty(stringList)) {
            return gagUids;
        }
        gagUids.addAll(stringList);
        return getRedisGagUserList(end + 1, gagUids);
    }

    /**
     * 获取数据
     */
    public static List<String> getData(int start, int end) {
        List<String> list = new ArrayList<>();
        if (count == 10) {
            return null;
        }
        System.out.println("start : " + start + " end : " + end + "");
        for (int i = start; i <= end; i++) {
            list.add(String.valueOf(i));
        }
        count++;
        return list;
    }


}
