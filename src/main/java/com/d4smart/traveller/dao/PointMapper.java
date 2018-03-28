package com.d4smart.traveller.dao;

import com.d4smart.traveller.pojo.Point;

public interface PointMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Point record);

    int insertSelective(Point record);

    Point selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Point record);

    int updateByPrimaryKey(Point record);
}