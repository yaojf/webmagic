package com.yaojiafeng.web.service.impl;

import com.yaojiafeng.web.common.Constants;
import com.yaojiafeng.web.dao.AppRankDao;
import com.yaojiafeng.web.domain.AppRank;
import com.yaojiafeng.web.service.AppRankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by yaojiafeng on 2017/6/11 下午12:01.
 */
@Service
public class AppRankServiceImpl implements AppRankService {

    @Autowired
    private AppRankDao appRankDao;


    @Override
    public List<AppRank> findAll() {
        return appRankDao.selectByExample(null);
    }

    @Override
    public int insertBatch(List<AppRank> appRankList) {
        for (AppRank appRank : appRankList) {
            Date date = new Date();
            appRank.setCreateTime(date);
            appRank.setCreatePerson(Constants.SYSTEM_PERSON);
            appRank.setUpdateTime(date);
            appRank.setUpdatePerson(Constants.SYSTEM_PERSON);
        }
        return appRankDao.insertBatch(appRankList);
    }
}
