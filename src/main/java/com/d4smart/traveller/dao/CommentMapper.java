package com.d4smart.traveller.dao;

import com.d4smart.traveller.pojo.Comment;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CommentMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Comment record);

    int insertSelective(Comment record);

    Comment selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Comment record);

    int updateByPrimaryKey(Comment record);

    int like(Integer id);

    int unlike(Integer id);

    List<Comment> getCommentsByPage(@Param("userId") Integer userId, @Param("guideId") Integer guideId,
                                    @Param("offset") Integer offset, @Param("limit") Integer limit);

    int getCommentCount(@Param("userId") Integer userId, @Param("guideId") Integer guideId);
}