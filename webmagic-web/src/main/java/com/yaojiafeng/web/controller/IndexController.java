package com.yaojiafeng.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by yaojiafeng on 2017/6/11 上午11:34.
 */
@Controller
public class IndexController {

    @RequestMapping("/")
    public String index() {
        return "/index";
    }

}
