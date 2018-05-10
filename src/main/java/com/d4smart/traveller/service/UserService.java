package com.d4smart.traveller.service;

import com.d4smart.traveller.common.Const;
import com.d4smart.traveller.common.PageInfo;
import com.d4smart.traveller.common.ServerResponse;
import com.d4smart.traveller.dao.CollectionMapper;
import com.d4smart.traveller.dao.FollowMapper;
import com.d4smart.traveller.dao.GuideMapper;
import com.d4smart.traveller.dao.UserMapper;
import com.d4smart.traveller.pojo.Guide;
import com.d4smart.traveller.pojo.User;
import com.d4smart.traveller.util.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by d4smart on 2018/3/30 9:21
 */
@Service("userService")
public class UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private FollowMapper followMapper;

    @Autowired
    private GuideMapper guideMapper;

    @Autowired
    private CollectionMapper collectionMapper;

    public ServerResponse register(User user) {
        if (user.getUsername() == null || user.getPassword() == null) {
            return ServerResponse.createByErrorMessage("用户信息不完整");
        }
        if (user.getGender() != null && !Arrays.asList(Const.GENDER).contains(user.getGender())) {
            return ServerResponse.createByErrorMessage("用户性别填写错误");
        }

        // 注册信息唯一性检查
        if (userMapper.checkUnique(Const.USERNAME, user.getUsername()) > 0) {
            return ServerResponse.createByErrorMessage("用户名已存在");
        }
        if (user.getPhone() != null && userMapper.checkUnique(Const.PHONE, user.getPhone()) > 0) {
            return ServerResponse.createByErrorMessage("手机号已存在");
        }

        // 用户信息设置
        user.setPassword(MD5Util.MD5EncodeUtf8(user.getPassword()));
        user.setCanLogin(null);
        user.setCanPublish(null);
        user.setIsAdmin(null);

        int count = userMapper.insertSelective(user);
        if (count == 0) {
            return ServerResponse.createByErrorMessage("注册失败");
        }

        return ServerResponse.createBySuccessMessage("注册成功");
    }

    public ServerResponse<User> login(String username, String password) {
        if (username == null || password == null) {
            return ServerResponse.createByErrorMessage("用户名或密码不能为空");
        }

        password = MD5Util.MD5EncodeUtf8(password);

        User user = userMapper.login(username, password);
        if (user == null) {
            return ServerResponse.createByErrorMessage("用户名与密码不匹配");
        }
        if (!user.getCanLogin()) {
            return ServerResponse.createByErrorMessage("暂时不可以登陆，如有疑问，请联系后台管理员");
        }

        return ServerResponse.createBySuccess("登陆成功", user);
    }

    public ServerResponse get(Integer id) {
        if (id == null) {
            return ServerResponse.createByErrorMessage("参数错误");
        }

        User user = userMapper.selectByPrimaryKey(id);
        if (user == null) {
            return ServerResponse.createByErrorMessage("用户不存在");
        }

        user.setCanLogin(null);
        user.setCanPublish(null);
        user.setIsAdmin(null);

        int follower = followMapper.getCount(null, id);
        int following = followMapper.getCount(id, null);
        int collection = collectionMapper.getCount(id);

        Map<String, Object> map = new HashMap<>();
        map.put("user", user);
        map.put("follower", follower);
        map.put("following", following);
        map.put("collection", collection);

        return ServerResponse.createBySuccess(map);
    }

    public ServerResponse<User> update(User user) {
        if (user.getGender() != null && !Arrays.asList(Const.GENDER).contains(user.getGender())) {
            return ServerResponse.createByErrorMessage("用户性别填写错误");
        }

        // 注册信息唯一性检查
        if (user.getUsername() != null && userMapper.checkUnique(Const.USERNAME, user.getUsername()) > 0) {
            return ServerResponse.createByErrorMessage("用户名已存在");
        }
        if (user.getPhone() != null && userMapper.checkUnique(Const.PHONE, user.getPhone()) > 0) {
            return ServerResponse.createByErrorMessage("手机号已存在");
        }

        // 用户信息设置
        if (user.getPassword() != null) {
            user.setPassword(MD5Util.MD5EncodeUtf8(user.getPassword()));
        }
        user.setCanLogin(null);
        user.setCanPublish(null);
        user.setIsAdmin(null);

        int count = userMapper.updateByPrimaryKeySelective(user);
        if (count == 0) {
            return ServerResponse.createByErrorMessage("更新用户信息失败");
        }

        user = userMapper.selectByPrimaryKey(user.getId());

        return ServerResponse.createBySuccess("更新用户信息成功", user);
    }

    public ServerResponse checkValid(String type, String value) {
        // 校验
        if (Const.USERNAME.equals(type)) {
            if (userMapper.checkUnique(type, value) > 0) {
                return ServerResponse.createByErrorMessage("用户名已存在");
            }
        } else if (Const.PHONE.equals(type)) {
            if (userMapper.checkUnique(type, value) > 0) {
                return ServerResponse.createByErrorMessage("手机号已存在");
            }
        } else {
            return ServerResponse.createByErrorMessage("参数错误");
        }

        return ServerResponse.createBySuccessMessage("校验成功");
    }

    public ServerResponse follow(Integer userId, Integer followId) {
        if (followId == null) {
            return ServerResponse.createByErrorMessage("参数错误");
        }

        if (userId.equals(followId)) {
            return ServerResponse.createByErrorMessage("自己不能关注自己");
        }

        User user = userMapper.selectByPrimaryKey(followId);
        if (user == null) {
            return ServerResponse.createByErrorMessage("要关注的用户不存在");
        }

        try {
            int count = followMapper.insert(userId, followId);
            if (count == 0) {
                return ServerResponse.createByErrorMessage("关注用户失败");
            }
        } catch (DuplicateKeyException e) {
            // 唯一键冲突
            return ServerResponse.createByErrorMessage("您已经关注过这个用户");
        }

        return ServerResponse.createBySuccessMessage("关注用户成功");
    }

    public ServerResponse unfollow(Integer userId, Integer followId) {
        if (followId == null) {
            return ServerResponse.createByErrorMessage("参数错误");
        }

        if (userId.equals(followId)) {
            return ServerResponse.createByErrorMessage("自己不能取消关注自己");
        }

        User user = userMapper.selectByPrimaryKey(followId);
        if (user == null) {
            return ServerResponse.createByErrorMessage("要取消关注的用户不存在");
        }

        int count = followMapper.delete(userId, followId);
        if (count == 0) {
            return ServerResponse.createByErrorMessage("取消关注用户失败");
        }

        return ServerResponse.createBySuccessMessage("取消关注用户成功");
    }

    public ServerResponse isFollowing(Integer userId, Integer followId) {
        if (followId == null) {
            return ServerResponse.createByErrorMessage("参数错误");
        }

        int count = followMapper.getCount(userId, followId);
        if (count == 0) {
            return ServerResponse.createByErrorMessage("没有关注这个用户");
        }

        return ServerResponse.createBySuccessMessage("关注了这个用户");
    }

    public ServerResponse<PageInfo> follows(Integer userId, Integer followId, int pageNum, int pageSize) {
        int offset = (pageNum - 1) * pageSize;
        List<User> follows = followMapper.getByPage(userId, followId, offset, pageSize);
        int count = followMapper.getCount(userId, followId);

        PageInfo pageInfo = new PageInfo(pageNum, pageSize, count);
        pageInfo.setList(follows);

        return ServerResponse.createBySuccess(pageInfo);
    }

    public ServerResponse<PageInfo> dynamics(Integer id, int pageNum, int pageSize) {
        int offset = (pageNum - 1) * pageSize;
        List<Guide> guides = guideMapper.getDynamicsByPage(id, offset, pageSize);
        int count = guideMapper.getDynamicCount(id) + guideMapper.getCount(null, id, null, true);

        PageInfo pageInfo = new PageInfo(pageNum, pageSize, count);
        pageInfo.setList(guides);

        return ServerResponse.createBySuccess(pageInfo);
    }
}
