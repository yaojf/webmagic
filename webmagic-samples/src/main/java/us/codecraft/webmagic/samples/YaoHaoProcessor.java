/**
 * qccr.com Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package us.codecraft.webmagic.samples;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import us.codecraft.webmagic.*;
import us.codecraft.webmagic.pipeline.Pipeline;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Selectable;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author yaojiafeng
 * @since $Revision:1.0.0, $Date: 2017/3/27 下午2:55 $
 */
public class YaoHaoProcessor implements PageProcessor {

    public static final String URL_GET = "http://apply.hzcb.gov.cn/apply/app/status/norm/person?pageNo=";

    private static final Logger logger = LoggerFactory.getLogger(YaoHaoProcessor.class);

    private Site site = Site
            .me()
            .setDomain("apply.hzcb.gov.cn/")
            .setSleepTime(3000)
            .setUserAgent(
                    "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_7_2) AppleWebKit/537.31 (KHTML, like Gecko) Chrome/26.0.1410.65 Safari/537.31");

    private Set<String> pageSet = new HashSet<String>();


    @Override
    public void process(Page page) {
        //解析后几页，用set去重
//        List<String> pageNumList = page.getHtml().xpath("//div[@class=\"pageturn taiji_pager\"]").xpath("//a[@class=\"taiji_pager_item\"]/text()").all();
//        for (String pageNum : pageNumList) {
//            if (pageSet.add(pageNum)) {
//                page.addTargetRequest(URL_GET + pageNum);
//            }
//        }
        String curNum = page.getUrl().regex("http://apply\\.hzcb\\.gov\\.cn/apply/app/status/norm/person\\?pageNo=(\\d+)").get();
        for (int i = 1; i <= 5; i++) {
            String nextNum = Integer.parseInt(curNum) + i + "";
            if (pageSet.add(nextNum)) {
                page.addTargetRequest(URL_GET + nextNum);
            }
        }

        //解析当前中签人员
        StringBuilder sb = new StringBuilder();
        List<Selectable> nodes = page.getHtml().xpath("//table[@class=\"ge2_content\"]//tr[@class=\"content_data\"]").nodes();
        for (Selectable node : nodes) {
            String code = node.xpath("//td[1]/text()").get();
            String name = node.xpath("//td[2]/text()").get();
            sb.append("申请编码:").append(code).append(",").append("姓名:").append(name).append("\n");
        }

        page.putField("", sb);
    }

    @Override
    public Site getSite() {
        return site;
    }


    public static void main(String[] args) {
        Spider.create(new YaoHaoProcessor()).addPipeline(new Pipeline() {
            @Override
            public void process(ResultItems resultItems, Task task) {
                logger.info("get page {}", resultItems.getRequest().getUrl());
                for (Map.Entry<String, Object> entry : resultItems.getAll().entrySet()) {
                    System.out.println(entry.getValue());
                }
            }
        }).addUrl("http://apply.hzcb.gov.cn/apply/app/status/norm/person?pageNo=1").thread(1)
                .run();
    }
}
