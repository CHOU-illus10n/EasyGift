package com.ncepu.eg.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.ncepu.eg.mapper.UserMapper;
import com.ncepu.eg.pojo.Admin;
import com.ncepu.eg.pojo.PageBean;
import com.ncepu.eg.pojo.User;
import com.ncepu.eg.service.UserService;
import com.ncepu.eg.utils.Md5Util;
import com.ncepu.eg.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Override
    public Admin findByUserName(String username) {
        Admin u = userMapper.findByUserName(username);
        return u;
    }

    @Override
    public void register(String username, String password) {
        //加密
        String md5String = Md5Util.getMD5String(password);
        //添加
        userMapper.add(username,md5String);
    }

    @Override
    public void update(User user) {
        user.setUpdateTime(LocalDateTime.now());
        userMapper.update(user);
    }

    @Override
    public void updateAvatar(String avatarUrl) {
        Map<String,Object> map = ThreadLocalUtil.get();
        Integer id = (Integer) map.get("id");
        userMapper.updateAvatar(avatarUrl,id);
    }

    @Override
    public void updatePwd(String newPwd) {
        Map<String,Object> map = ThreadLocalUtil.get();
        Integer id = (Integer) map.get("id");
        userMapper.updatePwd(Md5Util.getMD5String(newPwd),id);
    }

    @Override
    public PageBean<User> list(Integer pageNum, Integer pageSize, String nickName) {
        //1.创建PageBean对象
        PageBean<User> pbi = new PageBean<>();

        //2.开启分页查询 PageHelper
        PageHelper.startPage(pageNum, pageSize);

//        Map<String,Object> map = ThreadLocalUtil.get();
//        Integer userId = (Integer) map.get("id");
        List<User> as = userMapper.list(nickName);
        //Page中提供了方法,可以获取PageHelper分页查询后 得到的总记录条数和当前页数据
        Page<User> p = (Page<User>) as;

        //把数据填充到PageBean对象中
        pbi.setTotal(p.getTotal());
        pbi.setItems(p.getResult());
        return pbi;
    }

    @Override
    public User getOne(String nickName) {

        return userMapper.getOne(nickName);
    }

    @Override
    public void delete(Integer id) {
        userMapper.delete(id);
    }


}
