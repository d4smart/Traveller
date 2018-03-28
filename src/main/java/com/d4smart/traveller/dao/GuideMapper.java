package com.d4smart.traveller.dao;

import com.d4smart.traveller.pojo.Guide;

public interface GuideMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Guide record);

    int insertSelective(Guide record);

    Guide selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Guide record);

    int updateByPrimaryKey(Guide record);
}