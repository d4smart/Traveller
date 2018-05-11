package com.d4smart.traveller.dao;

import com.d4smart.traveller.pojo.Trail;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TrailMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Trail record);

    int insertSelective(Trail record);

    Trail selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Trail record);

    int updateByPrimaryKey(Trail record);

    List<Trail> getByPage(@Param("userId") Integer userId, @Param("offset") Integer offset, @Param("limit") Integer limit);

    int getCount(Integer userId);
}