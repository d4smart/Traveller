package com.d4smart.traveller.dao;

import com.d4smart.traveller.pojo.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface FollowMapper {
    int insert(@Param("userId") Integer userId, @Param("followId") Integer followId);

    int delete(@Param("userId") Integer userId, @Param("followId") Integer followId);

    List<User> getByPage(@Param("userId") Integer userId, @Param("followId") Integer followId,
                         @Param("offset") Integer offset, @Param("limit") Integer limit);

    int getCount(@Param("userId") Integer userId, @Param("followId") Integer followId);
}