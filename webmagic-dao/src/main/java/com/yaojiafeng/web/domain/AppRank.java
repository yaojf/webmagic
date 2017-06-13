package com.yaojiafeng.web.domain;

import java.io.Serializable;
import java.util.Date;

public class AppRank implements Serializable {
    private Integer id;

    private Integer timeDimension;

    private String timeValue;

    private Integer typeId;

    private String typeName;

    private Integer rank;

    private String appName;

    private Double coverage;

    private Double activeRate;

    private Date createTime;

    private String createPerson;

    private Date updateTime;

    private String updatePerson;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTimeDimension() {
        return timeDimension;
    }

    public void setTimeDimension(Integer timeDimension) {
        this.timeDimension = timeDimension;
    }

    public String getTimeValue() {
        return timeValue;
    }

    public void setTimeValue(String timeValue) {
        this.timeValue = timeValue;
    }

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public Double getCoverage() {
        return coverage;
    }

    public void setCoverage(Double coverage) {
        this.coverage = coverage;
    }

    public Double getActiveRate() {
        return activeRate;
    }

    public void setActiveRate(Double activeRate) {
        this.activeRate = activeRate;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCreatePerson() {
        return createPerson;
    }

    public void setCreatePerson(String createPerson) {
        this.createPerson = createPerson;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getUpdatePerson() {
        return updatePerson;
    }

    public void setUpdatePerson(String updatePerson) {
        this.updatePerson = updatePerson;
    }

    @Override
    public String toString() {
        return "AppRank{" +
                "id=" + id +
                ", timeDimension=" + timeDimension +
                ", timeValue='" + timeValue + '\'' +
                ", typeId=" + typeId +
                ", typeName='" + typeName + '\'' +
                ", rank=" + rank +
                ", appName='" + appName + '\'' +
                ", coverage=" + coverage +
                ", activeRate=" + activeRate +
                ", createTime=" + createTime +
                ", createPerson='" + createPerson + '\'' +
                ", updateTime=" + updateTime +
                ", updatePerson='" + updatePerson + '\'' +
                '}';
    }
}