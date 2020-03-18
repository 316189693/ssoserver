package com.example.ssoserver.dao;

import com.example.ssoserver.entity.SsoUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface SsoUserDao {
    int deleteByPrimaryKey(Integer id);

    int insert(SsoUser record);

    int insertSelective(SsoUser record);

    SsoUser selectByPrimaryKey(Integer id);

    SsoUser selectByUserNameOrEmail(@Param("userName") String userName,@Param("email") String email);

    int updateByPrimaryKeySelective(SsoUser record);

    int updateByPrimaryKey(SsoUser record);
}