package com.example.ssoserver.dao;

import com.example.ssoserver.entity.Menu;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MenuDao {
    int deleteByPrimaryKey(Integer id);

    int insert(Menu record);

    int insertSelective(Menu record);

    Menu selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Menu record);

    int updateByPrimaryKey(Menu record);
}