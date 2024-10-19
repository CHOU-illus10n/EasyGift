package com.ncepu.easygift.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ncepu.easygift.exception.XzException;
import com.ncepu.easygift.pojo.User;
import com.ncepu.easygift.result.R;
import com.ncepu.easygift.result.REnum;
import com.ncepu.easygift.service.UserInfoService;
import com.ncepu.easygift.util.JwtHelper;
import com.ncepu.easygift.vo.AppLoginVo;
import com.ncepu.easygift.vo.UserRegisterVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;


@RestController
@RequestMapping("/easygift")
@CrossOrigin
public class UserInfoController {

    @Autowired
    private UserInfoService userInfoService;


    // 前台部分用户注册接口
    @PostMapping("/register")
    public R register(@RequestBody UserRegisterVo registerVo) {
        userInfoService.register(registerVo);
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("open_id", registerVo.getOpenId());
        User user = userInfoService.getOne(queryWrapper);
        String token = JwtHelper.createToken(user.getUserId(), user.getCommunityId());
        return R.ok().message("用户注册成功").data("token",token);
    }

    // 前台部分登录接口
    @PostMapping("/login")
    public R login(@RequestBody AppLoginVo appLoginVo){
        String token = userInfoService.appLogin(appLoginVo);
        return R.ok().message("用户登录成功").data("token", token);
    }

    // 前台获取用户信息接口
    @GetMapping("/userinfo")
    public R getUserInfo(HttpServletRequest request){
        String token = request.getHeader("token");
        if (token==null)
            throw new XzException(REnum.LoginError.getCode(), "获取token失败");
        Long userid = JwtHelper.getUserId(token);
        System.out.println(userid);
        User user = userInfoService.GetUserInfo(userid);
        return R.ok().message("获取用户基本信息成功").data("user", user);
    }

    @PostMapping("/update")
    public R update(HttpServletRequest request,@RequestBody User user){
        String token = request.getHeader("token");
        Long userId = JwtHelper.getUserId(token);
        user.setUserId(userId);
        userInfoService.updateInfo(user);
        return R.ok().message("修改用户信息成功");
    }
}
