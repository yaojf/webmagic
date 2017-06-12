package com.yaojiafeng.crawler.talkingdata.processor;

import com.yaojiafeng.crawler.talkingdata.enum2.DateType;
import com.yaojiafeng.crawler.talkingdata.model.DateSection;
import com.yaojiafeng.crawler.talkingdata.model.Type;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Html;
import us.codecraft.webmagic.selector.Selectable;

import java.io.LineNumberReader;
import java.io.StringReader;
import java.util.*;

/**
 * Created by yaojiafeng on 2017/6/10 下午11:04.
 */
public class AppRankProcessor implements PageProcessor {

    public static final String URL_GET = "http://mi.talkingdata.com/app-rank.json?date=%s&typeId=%s&rankType=a&dateType=%s";


    private static final Logger logger = LoggerFactory.getLogger(AppRankProcessor.class);


    public static final String MIN_WEEK = "minWeek";
    public static final String MAX_WEEK = "maxWeek";
    public static final String MIN_MONTH = "minMonth";
    public static final String MAX_MONTH = "maxMonth";
    public static final String MIN_Q = "minQ";
    public static final String MAX_Q = "maxQ";

    private DateSection dateSection = new DateSection();

    private Site site = Site
            .me()
            .setDomain("mi.talkingdata.com")
            .setSleepTime(1000)
            .setUserAgent(
                    "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_7_2) AppleWebKit/537.31 (KHTML, like Gecko) Chrome/26.0.1410.65 Safari/537.31");

    private Set<String> pageSet = new HashSet<String>();

    private List<Type> typeList = new ArrayList<>();

    @Override
    public void process(Page page) {
        //1、获取所有类型
        List<String> categorys = page.getHtml().xpath("//div[@class=\"app-menu\"]//ol//li/a").all();
        if (CollectionUtils.isNotEmpty(categorys)) {
            for (String category : categorys) {
                Html tempHtml = Html.create("<table>" + category + "</table>");
                String typeId = tempHtml.xpath("//a/@td-data").toString();
                String typeName = tempHtml.xpath("//a/text()").toString();

                typeList.add(new Type(typeId, typeName));
            }
        }
        //2、获取时间区间
        Selectable regex = page.getHtml().regex("<script>(.*window.dateLimit.*?)</script>");
        if (regex != null) {
            LineNumberReader lineNumberReader = new LineNumberReader(new StringReader(regex.toString()));
            try {
                String line;
                while ((line = lineNumberReader.readLine().trim()) != null) {
                    if (line.contains(MIN_WEEK)) {
                        setDate(MIN_WEEK, line);
                    } else if (line.contains(MAX_WEEK)) {
                        setDate(MAX_WEEK, line);
                    } else if (line.contains(MIN_MONTH)) {
                        setDate(MIN_MONTH, line);
                    } else if (line.contains(MAX_MONTH)) {
                        setDate(MAX_MONTH, line);
                    } else if (line.contains(MIN_Q)) {
                        setDate(MIN_Q, line);
                    } else if (line.contains(MAX_Q)) {
                        setDate(MAX_Q, line);
                    }
                }
            } catch (Exception ex) {

            }
        }

        //3.爬取数据
        List<String> urls = new ArrayList<>();
        for (DateType dateType : DateType.values()) {
            String name = dateType.name().toLowerCase();
            List<String> dates = dateSection.getDate(dateType);
            for (String date : dates) {
                for (Type type : typeList) {
                    urls.add(String.format(URL_GET, date, type.getId(), name));
                }
            }
        }

        System.out.println(urls);

    }


    private void setDate(String type, String line) {
        String date = line.substring(line.indexOf(":\"") + 2, line.lastIndexOf("\""));
        if (type.equals(MIN_WEEK)) {
            dateSection.setMinWeek(date);
        } else if (type.equals(MAX_WEEK)) {
            dateSection.setMaxWeek(date);
        } else if (type.equals(MIN_MONTH)) {
            dateSection.setMinMonth(date);
        } else if (type.equals(MAX_MONTH)) {
            dateSection.setMaxMonth(date);
        } else if (type.equals(MIN_Q)) {
            dateSection.setMinQ(date);
        } else if (type.equals(MAX_Q)) {
            dateSection.setMaxQ(date);
        }
    }

    @Override
    public Site getSite() {
        return site;
    }

    public static void main(String[] args) {
        Spider.create(new AppRankProcessor()).addUrl("http://mi.talkingdata.com/app-rank.html").thread(1)
                .run();

    }
}
