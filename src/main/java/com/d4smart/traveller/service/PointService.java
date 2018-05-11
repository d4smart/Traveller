package com.d4smart.traveller.service;

import com.d4smart.traveller.common.PageInfo;
import com.d4smart.traveller.common.ResponseCode;
import com.d4smart.traveller.common.ServerResponse;
import com.d4smart.traveller.dao.PointMapper;
import com.d4smart.traveller.dao.TrailMapper;
import com.d4smart.traveller.pojo.Point;
import com.d4smart.traveller.pojo.Trail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by d4smart on 2018/5/11 11:03
 */
@Service("pointService")
public class PointService {

    @Autowired
    private PointMapper pointMapper;

    @Autowired
    private TrailMapper trailMapper;

    public ServerResponse add(Point point, Integer userId) {
        if (point.getTrailId() == null || point.getSequence() == null || point.getLongitude() == null || point.getLatitude() == null) {
            return ServerResponse.createByErrorMessage("轨迹点信息不完整");
        }

        Trail trail = trailMapper.selectByPrimaryKey(point.getTrailId());
        if (trail == null) {
            return ServerResponse.createByErrorMessage("轨迹点所属轨迹不存在");
        }
        if (!userId.equals(trail.getUserId())) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.PERMISSION_DENIED.getCode(), ResponseCode.PERMISSION_DENIED.getDesc());
        }

        int count = pointMapper.insertSelective(point);
        if (count == 0) {
            return ServerResponse.createByErrorMessage("轨迹点添加失败");
        }

        return ServerResponse.createBySuccessMessage("轨迹点添加成功");
    }

    public ServerResponse edit(Point point, Integer userId) {
        if (point.getId() == null) {
            return ServerResponse.createByErrorMessage("参数错误");
        }

        Point old = pointMapper.selectByPrimaryKey(point.getId());
        if (old == null) {
            return ServerResponse.createByErrorMessage("轨迹点不存在");
        }

        Trail trail = trailMapper.selectByPrimaryKey(old.getTrailId());
        if (!userId.equals(trail.getUserId())) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.PERMISSION_DENIED.getCode(), ResponseCode.PERMISSION_DENIED.getDesc());
        }

        point.setTrailId(null);

        int count = pointMapper.updateByPrimaryKeySelective(point);
        if (count == 0) {
            return ServerResponse.createByErrorMessage("轨迹点更新失败");
        }

        return ServerResponse.createBySuccessMessage("轨迹点更新成功");
    }

    public ServerResponse<PageInfo> list(Integer trailId, Integer pageNum, Integer pageSize) {
        if (trailId == null) {
            return ServerResponse.createByErrorMessage("参数错误");
        }

        int offset = (pageNum - 1) * pageSize;
        List<Point> points = pointMapper.getByPage(trailId, offset, pageSize);
        int count = pointMapper.getCount(trailId);

        PageInfo pageInfo = new PageInfo(pageNum, pageSize, count);
        pageInfo.setList(points);

        return ServerResponse.createBySuccess(pageInfo);
    }
}
