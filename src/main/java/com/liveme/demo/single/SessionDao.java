package com.liveme.demo.single;


import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class SessionDao {

//    private  BaseDao baseDao = BaseDao.getInstance(Constants.SESSION_MONGO);

    private String value;

    private static class SessionDaoHolder {
        private static final SessionDao instance = new SessionDao();
    }


    private static class SessionDaoHolderDataSource {
        private static final SessionDao instance = new SessionDao();
    }

    private SessionDao() {

    }

    public void initDataSource(String value) {

        this.value=value;
    }
    public static SessionDao getInstance() {
        return SessionDaoHolder.instance;
    }


    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
