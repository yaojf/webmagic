package com.yaojiafeng.crawler.talkingdata.enum2;

/**
 * Created by yaojiafeng on 2017/6/12 下午7:16.
 */
public enum DateType {
    W(1), M(2), Q(3);

    private final int tag;

    DateType(int tag) {
        this.tag = tag;
    }

    public int getTag() {
        return tag;
    }

    public static int getTag(String name) {
        for (DateType dateType : DateType.values()) {
            if (dateType.name().equalsIgnoreCase(name)) {
                return dateType.tag;
            }
        }
        return 0;
    }
}