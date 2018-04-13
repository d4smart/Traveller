package com.d4smart.traveller.dao;

import com.d4smart.traveller.pojo.Follow;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface FollowMapper {
    int insertSelective(Follow record);

    int delete(@Param("userId") Integer userId, @Param("followId") Integer followId);

    List<Follow> getFollowsByPage(@Param("userId") Integer userId, @Param("followId") Integer followId,
                                     @Param("offset") Integer offset, @Param("limit") Integer limit);

    int getFollowCount(@Param("userId") Integer userId, @Param("followId") Integer followId);
}