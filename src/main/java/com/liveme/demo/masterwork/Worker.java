package com.liveme.demo.masterwork;

import com.alibaba.fastjson.JSONObject;
import com.liveme.demo.util.DButil;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CopyOnWriteArrayList;

public class Worker implements Runnable {

    private static Logger logger = LoggerFactory.getLogger(Worker.class);
    private ConcurrentLinkedQueue<Task> workQueue;
    private CopyOnWriteArrayList<Object> resultMapList;


    public void setWorkQueue(ConcurrentLinkedQueue<Task> workQueue) {
        this.workQueue = workQueue;
    }

    public void setResultMap(CopyOnWriteArrayList<Object> resultMapList) {
        this.resultMapList = resultMapList;
    }


    public Worker(){
    }

    public void run() {
        while (true) {
           Task input = this.workQueue.poll();
            if (input == null) break;
            Object output = handle(input);
            if (null != output) {
                this.resultMapList.add(output);
            }
        }
    }

    private Object handle(Task task) {
        try {
            DBObject msgDB = findOne2(task.getValue(), task.getObject(), task.getTable());
            return msgDB;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
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
            long startTime = System.currentTimeMillis();
            DBCollection conn = DButil.getMap().get("chat").getCollection(collectionName);
            obj = conn.findOne(object);

            System.out.println("查询单条 耗时："+(System.currentTimeMillis()-startTime)+"ms");
        } catch (Exception e) {
            logger.error("", e);
            // throw new RuntimeException("db error", e);
        }
        return obj;
    }


}
