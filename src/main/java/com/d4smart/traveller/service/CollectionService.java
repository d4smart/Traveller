package com.d4smart.traveller.service;

import com.d4smart.traveller.common.PageInfo;
import com.d4smart.traveller.common.ServerResponse;
import com.d4smart.traveller.dao.CollectionMapper;
import com.d4smart.traveller.dao.GuideMapper;
import com.d4smart.traveller.pojo.Collection;
import com.d4smart.traveller.pojo.Guide;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by d4smart on 2018/4/11 17:00
 */
@Service("collectionService")
public class CollectionService {

    @Autowired
    private CollectionMapper collectionMapper;

    @Autowired
    private GuideMapper guideMapper;

    public ServerResponse add(Integer userId, Integer guideId) {
        if (guideId == null) {
            return ServerResponse.createByErrorMessage("参数错误");
        }

        Guide guide = guideMapper.selectByPrimaryKey(guideId);
        if (guide == null) {
            return ServerResponse.createByErrorMessage("要收藏的攻略不存在");
        }
        if (!guide.getIsPublished()) {
            return ServerResponse.createByErrorMessage("攻略未发布，不能收藏");
        }

        Collection collection = new Collection();
        collection.setUserId(userId);
        collection.setGuideId(guideId);

        try {
            int count = collectionMapper.insertSelective(collection);
            if (count == 0) {
                return ServerResponse.createByErrorMessage("添加收藏失败");
            }
        } catch (DuplicateKeyException e) {
            // 唯一键冲突，则重复收藏了攻略
            return ServerResponse.createByErrorMessage("不能重复收藏攻略");
        }

        return ServerResponse.createBySuccessMessage("添加收藏成功");
    }

    public ServerResponse<PageInfo> list(Integer userId, int pageNum, int pageSize) {
        int offset = (pageNum - 1) * pageSize;
        List<Guide> guides = collectionMapper.getByPage(userId, offset, pageSize);
        int count = collectionMapper.getCount(userId);

        PageInfo pageInfo = new PageInfo(pageNum, pageSize, count);
        pageInfo.setList(guides);

        return ServerResponse.createBySuccess(pageInfo);
    }

    public ServerResponse delete(Integer userId, Integer guideId) {
        if (guideId == null) {
            return ServerResponse.createByErrorMessage("参数错误");
        }

        Collection collection = collectionMapper.select(userId, guideId);
        if (collection == null) {
            return ServerResponse.createByErrorMessage("收藏不存在");
        }

        int count = collectionMapper.deleteByPrimaryKey(collection.getId());
        if (count == 0) {
            return ServerResponse.createByErrorMessage("取消收藏失败");
        }

        return ServerResponse.createBySuccessMessage("取消收藏成功");
    }
}
