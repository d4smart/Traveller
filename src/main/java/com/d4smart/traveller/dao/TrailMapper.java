package com.d4smart.traveller.dao;

import com.d4smart.traveller.pojo.Trail;

public interface TrailMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Trail record);

    int insertSelective(Trail record);

    Trail selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Trail record);

    int updateByPrimaryKey(Trail record);
}