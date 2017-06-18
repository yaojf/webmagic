package com.yaojiafeng.web.dao;

import com.yaojiafeng.web.domain.Task;
import com.yaojiafeng.web.domain.TaskConditions;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TaskDao {
    int countByExample(TaskConditions example);

    int deleteByExample(TaskConditions example);

    int deleteByPrimaryKey(Integer id);

    int insert(Task record);

    int insertSelective(Task record);

    List<Task> selectByExample(TaskConditions example);

    Task selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Task record, @Param("example") TaskConditions example);

    int updateByExample(@Param("record") Task record, @Param("example") TaskConditions example);

    int updateByPrimaryKeySelective(Task record);

    int updateByPrimaryKey(Task record);
}