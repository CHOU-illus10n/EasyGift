package com.ncepu.easygift.service;

import com.ncepu.easygift.pojo.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ncepu.easygift.vo.AppLoginVo;
import com.ncepu.easygift.vo.UserRegisterVo;


public interface UserInfoService extends IService<User> {


    void register(UserRegisterVo registerVo);

    String appLogin(AppLoginVo vo);

    User GetUserInfo(Long userId);

    void updateInfo(User user);
//
//    void appChangeUserInfo(Long userId, String account, Long communityId, MultipartFile img);
//
//    Map<String, Object> adminGetUserInfo(String token);
}
