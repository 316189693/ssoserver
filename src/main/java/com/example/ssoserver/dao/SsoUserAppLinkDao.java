package com.example.ssoserver.dao;

import com.example.ssoserver.entity.SsoUserAppLink;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SsoUserAppLinkDao {
    int deleteByPrimaryKey(Integer id);

    int insert(SsoUserAppLink record);

    int insertSelective(SsoUserAppLink record);

    SsoUserAppLink selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SsoUserAppLink record);

    int updateByPrimaryKey(SsoUserAppLink record);
}