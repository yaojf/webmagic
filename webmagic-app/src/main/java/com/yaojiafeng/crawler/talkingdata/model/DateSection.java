package com.yaojiafeng.crawler.talkingdata.model;

import com.yaojiafeng.crawler.talkingdata.enum2.DateType;
import org.apache.commons.lang3.time.DateUtils;
import org.joda.time.DateTime;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Created by yaojiafeng on 2017/6/12 下午7:13.
 */
public class DateSection {
    public static final String YYYY_MM_DD = "yyyy-MM-dd";

    public static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat(YYYY_MM_DD);

    private String minWeek;
    private String maxWeek;
    private String minMonth;
    private String maxMonth;
    private String minQ;
    private String maxQ;

    public String getMinWeek() {
        return minWeek;
    }

    public void setMinWeek(String minWeek) {
        this.minWeek = minWeek;
    }

    public String getMaxWeek() {
        return maxWeek;
    }

    public void setMaxWeek(String maxWeek) {
        this.maxWeek = maxWeek;
    }

    public String getMinMonth() {
        return minMonth;
    }

    public void setMinMonth(String minMonth) {
        this.minMonth = minMonth;
    }

    public String getMaxMonth() {
        return maxMonth;
    }

    public void setMaxMonth(String maxMonth) {
        this.maxMonth = maxMonth;
    }

    public String getMinQ() {
        return minQ;
    }

    public void setMinQ(String minQ) {
        this.minQ = minQ;
    }

    public String getMaxQ() {
        return maxQ;
    }

    public void setMaxQ(String maxQ) {
        this.maxQ = maxQ;
    }

    public List<String> getDate(DateType dateType) {
        try {
            List<String> dates = new ArrayList<>();
            if (dateType == DateType.W) {
                Date beginDate = DateUtils.parseDate(this.minWeek, YYYY_MM_DD);
                Date endDate = DateUtils.parseDate(this.maxWeek, YYYY_MM_DD);

                while (!beginDate.after(endDate)) {
                    dates.add(SIMPLE_DATE_FORMAT.format(beginDate));
                    DateTime dateTime = new DateTime(beginDate);
                    beginDate = dateTime.plusDays(7).toDate();
                }
            } else if (dateType == DateType.M) {
                Date beginDate = DateUtils.parseDate(this.minMonth, YYYY_MM_DD);
                Date endDate = DateUtils.parseDate(this.maxMonth, YYYY_MM_DD);

                while (!beginDate.after(endDate)) {
                    dates.add(SIMPLE_DATE_FORMAT.format(beginDate));
                    DateTime dateTime = new DateTime(beginDate);
                    beginDate = dateTime.plusMonths(1).toDate();
                }
            } else if (dateType == DateType.Q) {
                Date beginDate = DateUtils.parseDate(this.minQ, YYYY_MM_DD);
                Date endDate = DateUtils.parseDate(this.maxQ, YYYY_MM_DD);

                while (!beginDate.after(endDate)) {
                    dates.add(SIMPLE_DATE_FORMAT.format(beginDate));
                    DateTime dateTime = new DateTime(beginDate);
                    beginDate = dateTime.plusMonths(3).toDate();
                }
            }

            return dates;
        } catch (Exception ex) {

        }

        return Collections.emptyList();
    }
}
