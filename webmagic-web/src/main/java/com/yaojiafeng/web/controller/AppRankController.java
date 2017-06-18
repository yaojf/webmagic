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
        AppRankProcessor.main(null);

        return new Results(0, "success", null);
    }


}