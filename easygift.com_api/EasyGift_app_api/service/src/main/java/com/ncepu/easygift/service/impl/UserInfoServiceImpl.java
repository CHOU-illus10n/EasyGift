package com.ncepu.easygift.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ncepu.easygift.exception.XzException;
import com.ncepu.easygift.mapper.GiftInfoMapper;
import com.ncepu.easygift.pojo.User;
import com.ncepu.easygift.result.REnum;
import com.ncepu.easygift.service.CommunityInfoService;
import com.ncepu.easygift.service.ObsService;
import com.ncepu.easygift.service.UserInfoService;
import com.ncepu.easygift.mapper.UserMapper;
import com.ncepu.easygift.util.JwtHelper;
import com.ncepu.easygift.vo.AppLoginVo;
import com.ncepu.easygift.vo.UserRegisterVo;
import org.joda.time.DateTime;
import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;


@Service
public class UserInfoServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserInfoService{

    @Autowired
    private CommunityInfoService communityInfoService;
    @Autowired
    private ObsService obsService;
    @Autowired
    private GiftInfoMapper giftInfoMapper;
    @Autowired
    private UserMapper userMapper;


    @Transactional(rollbackFor = Exception.class)
    public void register(UserRegisterVo registerVo) {
        // 请求参数不完整
        if (registerVo.getOpenId()==null
                || registerVo.getNickName()==null
                || registerVo.getPhone()==null
                || registerVo.getFaceUrl() == null
                || registerVo.getCommunityId()==null) throw new XzException(REnum.ParamError.getCode(), REnum.ParamError.getMessage());
        QueryWrapper<User> userInfoQueryWrapper = new QueryWrapper<>();
        userInfoQueryWrapper.eq("open_id", registerVo.getOpenId());
        User existingUser = baseMapper.selectOne(userInfoQueryWrapper);
        if (existingUser != null){
            throw new XzException(REnum.DataExistError.getCode(), "用户信息已存在");
        }
        User newUser = new User();
        newUser.setProfileUrl(registerVo.getFaceUrl());
        newUser.setNickName(registerVo.getNickName());
        newUser.setPhone(registerVo.getPhone());
        newUser.setOpenId(registerVo.getOpenId());
        newUser.setCommunityId(registerVo.getCommunityId());
        baseMapper.insert(newUser); // 使用insert方法插入新用户
    }
//
    public String appLogin(AppLoginVo vo){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("open_id", vo.getOpenId());
        User user = baseMapper.selectOne(queryWrapper);
        if (user ==null){
            throw new XzException(REnum.NoDataError.getCode(), "用户登录信息不存在");
        }
        String token = JwtHelper.createToken(user.getUserId(), user.getCommunityId());
        return  token;
    }
//
    @Override
    public User GetUserInfo(Long userId) {
        User userInfo = baseMapper.selectById(userId);
        if (userInfo == null)
            throw new XzException(REnum.NoDataError.getCode(), "该用户信息不存在");
        return userInfo;
    }

    @Override
    public void updateInfo(User user) {
        user.setUpdateTime(LocalDateTime.now().toDate());
        userMapper.updateInfo(user);
    }



}




