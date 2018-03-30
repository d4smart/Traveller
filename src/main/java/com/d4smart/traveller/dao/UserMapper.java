package com.d4smart.traveller.dao;

import com.d4smart.traveller.pojo.User;
import org.apache.ibatis.annotations.Param;

public interface UserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    int checkUnique(@Param("type") String type, @Param("value") String value);

    User login(@Param("username") String username, @Param("password") String password);
}