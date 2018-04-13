package com.d4smart.traveller.dao;

import com.d4smart.traveller.pojo.Collection;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CollectionMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Collection record);

    int insertSelective(Collection record);

    Collection selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Collection record);

    int updateByPrimaryKey(Collection record);

    List<Collection> getByPage(@Param("userId") Integer userId, @Param("offset") Integer offset, @Param("limit") Integer limit);

    int getCount(@Param("userId") Integer userId);
}