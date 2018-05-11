package com.d4smart.traveller.dao;

import com.d4smart.traveller.pojo.Point;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PointMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Point record);

    int insertSelective(Point record);

    Point selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Point record);

    int updateByPrimaryKey(Point record);

    List<Point> getByPage(@Param("trailId") Integer trailId, @Param("offset") Integer offset, @Param("limit") Integer limit);

    int getCount(Integer trailId);
}