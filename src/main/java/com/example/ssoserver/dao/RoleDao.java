package com.example.ssoserver.dao;

import com.example.ssoserver.entity.Role;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RoleDao {
    int deleteByPrimaryKey(Integer id);

    int insert(Role record);

    int insertSelective(Role record);

    Role selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Role record);

    int updateByPrimaryKey(Role record);
}