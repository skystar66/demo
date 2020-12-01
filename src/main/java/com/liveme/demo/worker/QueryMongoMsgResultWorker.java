package com.liveme.demo.worker;

import com.alibaba.fastjson.JSONObject;
import com.liveme.demo.masterwork.Excutes;
import com.liveme.demo.util.DButil;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * 获取mongo 消息记录线程处理器
 * 由于mongo 分表存储，当历史记录多的时候 会影响系统吞吐量及QPS
 *
 * @author xuliang
 * @date:2020-09-22
 */
public class QueryMongoMsgResultWorker implements Callable<Integer> {

    private static Logger logger = LoggerFactory.getLogger(QueryMongoMsgResultWorker.class);

    private final int begin;
    private final int end;


    public QueryMongoMsgResultWorker(int begin, int end) {

        this.begin = begin;
        this.end = end;

    }

    @Override
    public Integer call() {
        int result = 0;
        try {
            System.out.println("begin :" + begin + " end : " + end);
            for (int i = begin; i < end; i++) {
                result += i;
                Thread.sleep(100);
            }
        } catch (Exception ex) {
            /**线程内抛出异常，不影响线程执行*/
            logger.error("QueryMongoMsgResultThread qrror : {}", ex);
        }

        return result;
    }


    /**
     * 条件查找唯一记录
     *
     * @param dbkey
     * @param object
     * @param collectionName
     * @return
     */
    public DBObject findOne2(String dbkey, BasicDBObject object, String collectionName) {
        DBObject obj = null;
        try {

            DBCollection conn = DButil.getMap().get("chat").getCollection(collectionName);
            long startTime = System.currentTimeMillis();
            obj = conn.findOne(object);

            System.out.println("查询单条 耗时：" + (System.currentTimeMillis() - startTime) + "ms");
        } catch (Exception e) {
            logger.error("", e);
            // throw new RuntimeException("db error", e);
        }
        return obj;
    }


}
