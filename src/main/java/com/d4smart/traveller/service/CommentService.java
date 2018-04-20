package com.d4smart.traveller.service;

import com.d4smart.traveller.common.Const;
import com.d4smart.traveller.common.PageInfo;
import com.d4smart.traveller.common.ResponseCode;
import com.d4smart.traveller.common.ServerResponse;
import com.d4smart.traveller.dao.CommentMapper;
import com.d4smart.traveller.dao.GuideMapper;
import com.d4smart.traveller.dao.cache.RedisDao;
import com.d4smart.traveller.pojo.Comment;
import com.d4smart.traveller.pojo.Guide;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by d4smart on 2018/4/11 14:07
 */
@Service("commentService")
public class CommentService {

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private GuideMapper guideMapper;

    @Autowired
    private RedisDao redisDao;

    public ServerResponse add(Integer userId, Integer guideId, String content) {
        if (guideId == null || content == null) {
            return ServerResponse.createByErrorMessage("参数不合法");
        }

        Guide guide = guideMapper.selectByPrimaryKey(guideId);
        if (guide == null) {
            return ServerResponse.createByErrorMessage("添加评论的攻略不存在");
        }
        if (!guide.getIsPublished()) {
            return ServerResponse.createByErrorMessage("攻略未发布，不能添加评论");
        }

        Comment comment = new Comment();
        comment.setUserId(userId);
        comment.setGuideId(guideId);
        comment.setContent(content);

        int count = commentMapper.insertSelective(comment);
        if (count == 0) {
            return ServerResponse.createByErrorMessage("添加评论失败");
        }

        count = guideMapper.increase(Const.COMMENT, guideId);
        if (count == 0) {
            return ServerResponse.createByErrorMessage("增加攻略评论数失败");
        }

        return ServerResponse.createBySuccessMessage("添加评论成功");
    }

    public ServerResponse edit(Comment comment, Integer userId) {
        if (comment.getId() == null || comment.getContent() == null) {
            return ServerResponse.createByErrorMessage("参数不合法");
        }

        Comment old = commentMapper.selectByPrimaryKey(comment.getId());
        if (old == null) {
            return ServerResponse.createByErrorMessage("评论不存在");
        }
        if (!userId.equals(old.getUserId())) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.PERMISSION_DENIED.getCode(), ResponseCode.PERMISSION_DENIED.getDesc());
        }

        comment.setUserId(null);
        comment.setGuideId(null);
        comment.setLikes(null);

        int count = commentMapper.updateByPrimaryKeySelective(comment);
        if (count == 0) {
            return ServerResponse.createByErrorMessage("编辑评论失败");
        }

        return ServerResponse.createBySuccessMessage("编辑评论成功");
    }

    public ServerResponse<Comment> get(Integer id) {
        if (id == null) {
            return ServerResponse.createByErrorMessage("参数错误");
        }

        Comment comment = commentMapper.selectByPrimaryKey(id);
        if (comment == null) {
            return ServerResponse.createByErrorMessage("评论不存在");
        }

        return ServerResponse.createBySuccess("获取评论成功", comment);
    }

    public ServerResponse<PageInfo> list(Integer userId, Integer guideId, Integer loginUserId, int pageNum, int pageSize) {
        if (userId == null && guideId == null) {
            return ServerResponse.createByErrorMessage("参数不合法");
        }
        if (userId != null && !userId.equals(loginUserId)) {
            return ServerResponse.createByErrorMessage("只能查看自己的评论列表");
        }

        int offset = (pageNum - 1) * pageSize;
        List<Comment> comments = commentMapper.getByPage(userId, guideId, offset, pageSize);
        int count = commentMapper.getCount(userId, guideId);

        PageInfo pageInfo = new PageInfo(pageNum, pageSize, count);
        pageInfo.setList(comments);

        return ServerResponse.createBySuccess(pageInfo);
    }

    public ServerResponse like(Integer id, Integer userId) {
        if (id == null) {
            return ServerResponse.createByErrorMessage("参数错误");
        }

        Comment comment = commentMapper.selectByPrimaryKey(id);
        if (comment == null) {
            return ServerResponse.createByErrorMessage("要点赞的评论不存在");
        }

        String key = "comment:" + id;
        if (!redisDao.like(key, userId)) {
            return ServerResponse.createByErrorMessage("请不要重复点赞");
        }

        int count = commentMapper.like(id);
        if (count == 0) {
            return ServerResponse.createByErrorMessage("增加评论点赞数失败");
        }

        return ServerResponse.createBySuccessMessage("点赞成功");
    }

    public ServerResponse unlike(Integer id, Integer userId) {
        if (id == null) {
            return ServerResponse.createByErrorMessage("参数错误");
        }

        Comment comment = commentMapper.selectByPrimaryKey(id);
        if (comment == null) {
            return ServerResponse.createByErrorMessage("要取消点赞的评论不存在");
        }

        String key = "comment:" + id;
        if (!redisDao.unlike(key, userId)) {
            return ServerResponse.createByErrorMessage("您之前没有点过赞，无法取消点赞");
        }

        int count = commentMapper.unlike(id);
        if (count == 0) {
            return ServerResponse.createByErrorMessage("减少评论点赞数失败");
        }

        return ServerResponse.createBySuccessMessage("取消点赞成功");
    }

    public ServerResponse delete(Integer id, Integer userId) {
        if (id == null) {
            return ServerResponse.createByErrorMessage("参数错误");
        }

        Comment comment = commentMapper.selectByPrimaryKey(id);
        if (comment == null) {
            return ServerResponse.createByErrorMessage("评论不存在");
        }
        if (!userId.equals(comment.getUserId())) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.PERMISSION_DENIED.getCode(), ResponseCode.PERMISSION_DENIED.getDesc());
        }

        int count = commentMapper.deleteByPrimaryKey(id);
        if (count == 0) {
            return ServerResponse.createByErrorMessage("删除评论失败");
        }

        count = guideMapper.decrease(Const.COMMENT, comment.getGuideId());
        if (count == 0) {
            return ServerResponse.createByErrorMessage("减少攻略评论数失败");
        }

        return ServerResponse.createBySuccessMessage("删除评论成功");
    }
}
