package com.yaojiafeng.crawler.talkingdata.processor;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.yaojiafeng.crawler.talkingdata.enum2.DateType;
import com.yaojiafeng.crawler.talkingdata.model.DateSection;
import com.yaojiafeng.crawler.talkingdata.model.Type;
import com.yaojiafeng.web.dao.AppRankDao;
import com.yaojiafeng.web.domain.AppRank;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.DispatcherServlet;
import us.codecraft.webmagic.*;
import us.codecraft.webmagic.pipeline.Pipeline;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.scheduler.QueueScheduler;
import us.codecraft.webmagic.selector.Html;
import us.codecraft.webmagic.selector.Selectable;

import java.io.LineNumberReader;
import java.io.StringReader;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by yaojiafeng on 2017/6/10 下午11:04.
 */
public class AppRankProcessor implements PageProcessor {

    public static final String URL_GET = "http://mi.talkingdata.com/app-rank.json?date=%s&typeId=%s&rankType=a&dateType=%s";

    public static final String ROOT_URL = "http://mi.talkingdata.com/app-rank.html";

    private static final Logger logger = LoggerFactory.getLogger(AppRankProcessor.class);

    public static final String URL_GET_REGEX = "http://mi\\.talkingdata\\.com/app-rank\\.json\\?date=(\\S+?)&typeId=(\\w+?)&rankType=a&dateType=(\\w+?)";

    public static final Pattern PATTERN = Pattern.compile(URL_GET_REGEX);

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
            .setTimeOut(10000)
            .setRetryTimes(3)
            .setUserAgent(
                    "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_7_2) AppleWebKit/537.31 (KHTML, like Gecko) Chrome/26.0.1410.65 Safari/537.31");

    private Set<String> pageSet = new HashSet<String>();

    private List<Type> typeList = new ArrayList<>();

    private Map<String, String> typeNameMap = new HashMap<>();

    @Override
    public void process(Page page) {
        if (page.getRequest().getUrl().equals(ROOT_URL)) {

            //1、获取所有类型
            List<String> categorys = page.getHtml().xpath("//div[@class=\"app-menu\"]//ol//li/a").all();
            if (CollectionUtils.isNotEmpty(categorys)) {
                for (String category : categorys) {
                    Html tempHtml = Html.create("<table>" + category + "</table>");
                    String typeId = tempHtml.xpath("//a/@td-data").toString();
                    String typeName = tempHtml.xpath("//a/text()").toString();

                    typeList.add(new Type(typeId, typeName));
                    typeNameMap.put(typeId, typeName);
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

            //3.获取爬取页
            if (pageSet.isEmpty()) {
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
                //去重
                pageSet.addAll(urls);
                page.addTargetRequests(new ArrayList<>(pageSet));
            }

        } else {
            //4.爬取具体数据
            /**
             * [{
             "activeRate": 0.0064968,
             "appIconUrl": "http://mi.talkingdata.com/repository/img/2593/2593/2593.png",
             "appId": 2593,
             "appName": "雷电2016（雷霆版）",
             "coverageChangeRate": 0.4415888,
             "coverageRate": 0.0233115,
             "newApp": false,
             "ranking": 1,
             "rankingChange": 1
             }]
             */
            String url = page.getRequest().getUrl();

            Matcher matcher = PATTERN.matcher(url);
            String date = null;
            String typeId = null;
            String dateType = null;

            if (matcher.find()) {
                date = matcher.group(1);
                typeId = matcher.group(2);
                dateType = matcher.group(3);
            }
            List<AppRank> appRankList = new ArrayList<>();
            String json = page.getHtml().xpath("//body/text()").toString();
            JSONArray jsonArray = JSONArray.parseArray(json);
            if (jsonArray != null && !jsonArray.isEmpty()) {
                for (int i = 0; i < jsonArray.size(); i++) {
                    AppRank appRank = new AppRank();
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    appRank.setTimeDimension(DateType.getTag(dateType));
                    appRank.setTimeValue(date);
                    appRank.setTypeId(Integer.valueOf(typeId));
                    appRank.setTypeName(typeNameMap.get(typeId));
                    appRank.setRank(jsonObject.getInteger("ranking"));
                    appRank.setAppName(jsonObject.getString("appName"));
                    appRank.setCoverage(jsonObject.getDouble("coverageRate"));
                    appRank.setActiveRate(jsonObject.getDouble("activeRate"));
                    appRankList.add(appRank);
                }
            }

            WebApplicationContext webApplicationContext = (WebApplicationContext) ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getAttribute(DispatcherServlet.WEB_APPLICATION_CONTEXT_ATTRIBUTE);

            AppRankDao appRankDao = webApplicationContext.getBean(AppRankDao.class);



            //没有任务结束
            if (((QueueScheduler) ((Spider) page.getTask()).getScheduler()).getLeftRequestsCount(null) == 0) {
                ((Spider) page.getTask()).stop();
            }
        }

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
        Spider.create(new AppRankProcessor()).addUrl(ROOT_URL).addPipeline(new Pipeline() {
            @Override
            public void process(ResultItems resultItems, Task task) {

            }
        }).thread(Runtime.getRuntime().availableProcessors())
                .run();

    }
}
