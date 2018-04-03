package com.d4smart.traveller.service;

import com.d4smart.traveller.common.Const;
import com.d4smart.traveller.common.PageInfo;
import com.d4smart.traveller.common.ResponseCode;
import com.d4smart.traveller.common.ServerResponse;
import com.d4smart.traveller.dao.GuideMapper;
import com.d4smart.traveller.pojo.Guide;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by d4smart on 2018/4/2 14:09
 */
@Service("guideService")
public class GuideService {

    @Autowired
    private GuideMapper guideMapper;

    public ServerResponse publish(Guide guide) {
        if (guide.getTitle() == null || guide.getPlaces() == null || guide.getContent() == null) {
            return ServerResponse.createByErrorMessage("攻略信息不完整");
        }

        guide.setViews(null);
        guide.setLikes(null);
        guide.setComments(null);
        guide.setScore(null);
        guide.setIsPublished(null);

        int count = guideMapper.insertSelective(guide);
        if (count == 0) {
            return ServerResponse.createByErrorMessage("发布攻略失败");
        }

        return ServerResponse.createBySuccessMessage("发布攻略成功");
    }

    public ServerResponse edit(Guide guide, Integer userId) {
        if (guide.getId() == null) {
            return ServerResponse.createByErrorMessage("参数不合法");
        }

        Guide old = guideMapper.selectByPrimaryKey(guide.getId());
        if (old == null) {
            return ServerResponse.createByErrorMessage("攻略不存在");
        }
        if (!userId.equals(old.getAuthorId())) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.PERMISSION_DENIED.getCode(), ResponseCode.PERMISSION_DENIED.getDesc());
        }

        guide.setAuthorId(null);
        guide.setViews(null);
        guide.setLikes(null);
        guide.setComments(null);
        guide.setScore(null);
        guide.setIsPublished(null);

        int count = guideMapper.updateByPrimaryKeySelective(guide);
        if (count == 0) {
            return ServerResponse.createByErrorMessage("更新攻略失败");
        }

        return ServerResponse.createBySuccessMessage("更新攻略成功");
    }

    public ServerResponse search(String title, String places, int pageNum, int pageSize) {
        if (title == null && places == null) {
            return ServerResponse.createByErrorMessage("搜索条件不合法");
        }

        int offset = (pageNum - 1) * pageSize;
        List<Guide> guides = guideMapper.getGuidesByPage(title, null, places, offset, pageSize);
        int count = guideMapper.getGuideCount(title, null, places);

        PageInfo pageInfo = new PageInfo(pageNum, pageSize, count);
        pageInfo.setList(guides);

        return ServerResponse.createBySuccess(pageInfo);
    }

    public ServerResponse list(Integer userId, int pageNum, int pageSize) {
        int offset = (pageNum - 1) * pageSize;
        List<Guide> guides = guideMapper.getGuidesByPage(null, userId, null, offset, pageSize);
        int count = guideMapper.getGuideCount(null, userId, null);

        PageInfo pageInfo = new PageInfo(pageNum, pageSize, count);
        pageInfo.setList(guides);

        return ServerResponse.createBySuccess(pageInfo);
    }

    public ServerResponse get(Integer id) {
        Guide guide = guideMapper.selectByPrimaryKey(id);
        if (guide == null) {
            return ServerResponse.createByErrorMessage("获取攻略失败");
        }

        int count = guideMapper.increase(Const.VIEW, id);
        if (count == 0) {
            return ServerResponse.createByErrorMessage("增加阅读数失败");
        }

        return ServerResponse.createBySuccess("获取攻略成功", guide);
    }

    public ServerResponse delete(Integer id, Integer userId) {
        Guide guide = guideMapper.selectByPrimaryKey(id);
        if (guide == null) {
            return ServerResponse.createByErrorMessage("要删除的攻略不存在");
        }
        if (!userId.equals(guide.getAuthorId())) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.PERMISSION_DENIED.getCode(), ResponseCode.PERMISSION_DENIED.getDesc());
        }

        int count = guideMapper.deleteByPrimaryKey(id);
        if (count == 0) {
            return ServerResponse.createByErrorMessage("删除攻略失败");
        }

        return ServerResponse.createBySuccessMessage("删除攻略成功");
    }
}
