package com.example.ssoserver.dao;

import com.example.ssoserver.entity.UserRoleLink;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserRoleLinkDao {
    int deleteByPrimaryKey(Integer id);

    int insert(UserRoleLink record);

    int insertSelective(UserRoleLink record);

    UserRoleLink selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserRoleLink record);

    int updateByPrimaryKey(UserRoleLink record);
}