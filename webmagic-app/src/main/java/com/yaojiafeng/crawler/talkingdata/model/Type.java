package com.yaojiafeng.crawler.talkingdata.model;

/**
 * Created by yaojiafeng on 2017/6/12 下午8:02.
 */
public class Type {

    private String id;

    private String name;

    public Type(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
