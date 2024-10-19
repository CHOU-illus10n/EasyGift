package com.ncepu.eg.service;

import com.ncepu.eg.pojo.Admin;
import com.ncepu.eg.pojo.PageBean;
import com.ncepu.eg.pojo.User;

public interface UserService {
    //根据用户名查询用户
    Admin findByUserName(String username);

    //注册
    void register(String username, String password);

    //更新
    void update(User user);

    //更新头像
    void updateAvatar(String avatarUrl);

    //更新密码
    void updatePwd(String newPwd);

    PageBean<User> list(Integer pageNum, Integer pageSize, String nickName);

    User getOne(String nickName);

    void delete(Integer id);
}
