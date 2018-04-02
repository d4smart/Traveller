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

    List<Guide> getGuidesByPage(@Param("title") String title, @Param("authorId") Integer authorId, @Param("places") String places,
                                @Param("offset") Integer offset, @Param("limit") Integer limit);

    int getGuideCount(@Param("title") String title, @Param("authorId") Integer authorId, @Param("places") String places);

    int increase(@Param("field") String field, @Param("id") Integer id);
}