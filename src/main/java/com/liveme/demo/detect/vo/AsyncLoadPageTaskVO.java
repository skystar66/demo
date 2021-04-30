package com.liveme.demo.detect.vo;


import com.liveme.demo.detect.SeasonMedianFieldCacheTypeEnum;

import java.io.Serializable;
import java.util.Set;

//@Data
//@Builder
//@AllArgsConstructor
//@NoArgsConstructor
public class AsyncLoadPageTaskVO implements Serializable {

    private String modelId;
    private int nextPosition;
    private int pageSize;
    private int pointsPerSeason;
    private boolean isDiff;
    private SeasonMedianFieldCacheTypeEnum typeEnum;
    private int source;
    private Set<Integer> pages;


    public String getModelId() {
        return modelId;
    }

    public void setModelId(String modelId) {
        this.modelId = modelId;
    }

    public int getNextPosition() {
        return nextPosition;
    }

    public void setNextPosition(int nextPosition) {
        this.nextPosition = nextPosition;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPointsPerSeason() {
        return pointsPerSeason;
    }

    public void setPointsPerSeason(int pointsPerSeason) {
        this.pointsPerSeason = pointsPerSeason;
    }

    public boolean isDiff() {
        return isDiff;
    }

    public void setDiff(boolean diff) {
        isDiff = diff;
    }

    public SeasonMedianFieldCacheTypeEnum getTypeEnum() {
        return typeEnum;
    }

    public void setTypeEnum(SeasonMedianFieldCacheTypeEnum typeEnum) {
        this.typeEnum = typeEnum;
    }

    public int getSource() {
        return source;
    }

    public void setSource(int source) {
        this.source = source;
    }

    public Set<Integer> getPages() {
        return pages;
    }

    public void setPages(Set<Integer> pages) {
        this.pages = pages;
    }
}
