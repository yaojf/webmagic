package com.yaojiafeng.web.dao;

import com.yaojiafeng.web.domain.AppRank;
import com.yaojiafeng.web.domain.AppRankConditions;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AppRankDao {
    int countByExample(AppRankConditions example);

    int deleteByExample(AppRankConditions example);

    int deleteByPrimaryKey(Integer id);

    int insert(AppRank record);

    int insertSelective(AppRank record);

    List<AppRank> selectByExample(AppRankConditions example);

    AppRank selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") AppRank record, @Param("example") AppRankConditions example);

    int updateByExample(@Param("record") AppRank record, @Param("example") AppRankConditions example);

    int updateByPrimaryKeySelective(AppRank record);

    int updateByPrimaryKey(AppRank record);
}