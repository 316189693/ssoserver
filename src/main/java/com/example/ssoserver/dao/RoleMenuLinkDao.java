package com.example.ssoserver.dao;

import com.example.ssoserver.entity.RoleMenuLink;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RoleMenuLinkDao {
    int deleteByPrimaryKey(Integer id);

    int insert(RoleMenuLink record);

    int insertSelective(RoleMenuLink record);

    RoleMenuLink selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(RoleMenuLink record);

    int updateByPrimaryKey(RoleMenuLink record);
}