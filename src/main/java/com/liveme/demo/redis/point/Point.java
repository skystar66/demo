package com.liveme.demo.redis.point;

import java.io.Serializable;

/**
 * @author yzj
 * @date 2020-11-21 19:49
 */
public class Point implements Serializable {
    Long timestamp;
    Double value;

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }


    //    @Override
//    public boolean equals(Object o) {
//        if (this == o) {
//            return true;
//        }
//        if (o == null || getClass() != o.getClass()) {
//            return false;
//        }
//        Point point = (Point) o;
//        return Objects.equals(timestamp, point.timestamp) &&
//            Objects.equals(value, point.value);
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(timestamp, value);
//    }
}
