package com.d4smart.traveller.dao;

import com.d4smart.traveller.pojo.Guide;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface GuideMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Guide record);

    int insertSelective(Guide record);

    Guide selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Guide record);

    int updateByPrimaryKey(Guide record);

    List<Guide> getOrdersByPage(@Param("title") String title, @Param("authorId") Integer authorId, @Param("places") String places,
                                @Param("offset") Integer offset, @Param("limit") Integer limit);

    int getOrderCount(@Param("title") String title, @Param("authorId") Integer authorId, @Param("places") String places);
}