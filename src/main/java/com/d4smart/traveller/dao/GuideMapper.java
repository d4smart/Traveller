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

    Guide get(Integer id);

    List<Guide> getByPage(@Param("title") String title, @Param("authorId") Integer authorId,
                          @Param("places") String places, @Param("isPublished") Boolean isPublished,
                          @Param("sortField") String sortField, @Param("offset") Integer offset, @Param("limit") Integer limit);

    int getCount(@Param("title") String title, @Param("authorId") Integer authorId,
                 @Param("places") String places, @Param("isPublished") Boolean isPublished);

    int increase(@Param("field") String field, @Param("id") Integer id);

    int decrease(@Param("field") String field, @Param("id") Integer id);

    List<Guide> getDynamicsByPage(@Param("userId") Integer userId, @Param("offset") Integer offset, @Param("limit") Integer limit);

    int getDynamicCount(Integer userId);
}