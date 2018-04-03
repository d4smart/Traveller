package com.d4smart.traveller.service;

import com.d4smart.traveller.common.Const;
import com.d4smart.traveller.common.ServerResponse;
import com.d4smart.traveller.dao.UserMapper;
import com.d4smart.traveller.pojo.User;
import com.d4smart.traveller.util.MD5Util;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by d4smart on 2018/3/30 9:21
 */
@Service("userService")
public class UserService {

    @Autowired
    private UserMapper userMapper;

    public ServerResponse register(User user) {
        if (user.getUsername() == null || user.getPassword() == null) {
            return ServerResponse.createByErrorMessage("用户信息不完整");
        }

        // 注册信息唯一性检查
        if (user.getUsername() != null && userMapper.checkUnique(Const.USERNAME, user.getUsername()) > 0) {
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
        password = MD5Util.MD5EncodeUtf8(password);

        User user = userMapper.login(username, password);
        if (user == null) {
            return ServerResponse.createByErrorMessage("用户名与密码不匹配");
        }

        user.setPassword(null);

        return ServerResponse.createBySuccess("登陆成功", user);
    }

    public ServerResponse<User> update(User user) {
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
        user.setPassword(null);

        return ServerResponse.createBySuccess("更新用户信息成功", user);
    }

    public ServerResponse checkValid(String type, String value) {
        if (StringUtils.isBlank(type)) {
            return ServerResponse.createByErrorMessage("参数错误");
        }

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
}
