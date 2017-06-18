package com.yaojiafeng.web.controller;

import com.yaojiafeng.crawler.talkingdata.processor.AppRankProcessor;
import com.yaojiafeng.web.common.Results;
import com.yaojiafeng.web.domain.AppRank;
import com.yaojiafeng.web.service.AppRankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import java.util.List;

/**
 * Created by yaojiafeng on 2017/6/11 下午12:07.
 */
@Controller
@RequestMapping("/appRank")
public class AppRankController {

    @Autowired
    private AppRankService appRankService;

    @RequestMapping("/list")
    public String list(ModelMap modelMap) {
        List<AppRank> appRankList = appRankService.findAll();
        modelMap.put("appRankList", appRankList);
        return "/appRank/list";
    }


    @RequestMapping("/crawler")
    @ResponseBody
    public Results crawler(ModelMap modelMap) {

        new Thread(new Runnable(){
            @Override
            public void run() {
                Spider.create(new AppRankProcessor()).addUrl(AppRankProcessor.ROOT_URL).addPipeline(new Pipeline() {
                    @Override
                    public void process(ResultItems resultItems, Task task) {

                        List<AppRank> appRankList = (List<AppRank>) resultItems.get(AppRankProcessor.RESULT);

                        if (appRankList != null && appRankList.size() > 0) {
                            appRankService.insertBatch(appRankList);
                        }
                    }
                }).thread(Runtime.getRuntime().availableProcessors()).run();
            }
        }).start();


        return new Results(0, "success", null);
    }


}