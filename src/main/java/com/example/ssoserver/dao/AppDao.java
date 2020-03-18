package com.example.ssoserver.dao;

import com.example.ssoserver.entity.App;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface AppDao {
    int deleteByPrimaryKey(Integer id);

    int insert(App record);

    int insertSelective(App record);

    App selectByPrimaryKey(Integer id);

    List<App> selectBySsoUserId(@Param("ssoUserId") String ssoUserId);

    int updateByPrimaryKeySelective(App record);

    int updateByPrimaryKey(App record);
}