package com.d4smart.traveller.service;

import com.d4smart.traveller.common.PageInfo;
import com.d4smart.traveller.common.ResponseCode;
import com.d4smart.traveller.common.ServerResponse;
import com.d4smart.traveller.dao.TrailMapper;
import com.d4smart.traveller.pojo.Trail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by d4smart on 2018/5/11 9:57
 */
@Service("trailService")
public class TrailService {

    @Autowired
    private TrailMapper trailMapper;

    public ServerResponse add(Trail trail) {
        if (trail.getName() == null || trail.getDescription() == null) {
            return ServerResponse.createByErrorMessage("轨迹信息不完整");
        }

        int count = trailMapper.insertSelective(trail);
        if (count == 0) {
            return ServerResponse.createByErrorMessage("轨迹添加失败");
        }

        return ServerResponse.createBySuccessMessage("轨迹添加成功");
    }

    public ServerResponse edit(Trail trail, Integer userId) {
        if (trail.getId() == null) {
            return ServerResponse.createByErrorMessage("参数错误");
        }

        Trail old = trailMapper.selectByPrimaryKey(trail.getId());
        if (old == null) {
            return ServerResponse.createByErrorMessage("轨迹不存在");
        }
        if (!userId.equals(old.getUserId())) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.PERMISSION_DENIED.getCode(), ResponseCode.PERMISSION_DENIED.getDesc());
        }

        trail.setUserId(null);

        int count = trailMapper.updateByPrimaryKeySelective(trail);
        if (count == 0) {
            return ServerResponse.createByErrorMessage("轨迹更新失败");
        }

        return ServerResponse.createBySuccessMessage("轨迹更新成功");
    }

    public ServerResponse<PageInfo> list(Integer userId, Integer pageNum, Integer pageSize) {
        int offset = (pageNum - 1) * pageSize;
        List<Trail> trails = trailMapper.getByPage(userId, offset, pageSize);
        int count = trailMapper.getCount(userId);

        PageInfo pageInfo = new PageInfo(pageNum, pageSize, count);
        pageInfo.setList(trails);

        return ServerResponse.createBySuccess(pageInfo);
    }

    public ServerResponse get(Integer id) {
        if (id == null) {
            return ServerResponse.createByErrorMessage("参数错误");
        }

        Trail trail = trailMapper.selectByPrimaryKey(id);
        if (trail == null) {
            return ServerResponse.createByErrorMessage("轨迹不存在");
        }

        return ServerResponse.createBySuccess("获取轨迹成功", trail);
    }
}
