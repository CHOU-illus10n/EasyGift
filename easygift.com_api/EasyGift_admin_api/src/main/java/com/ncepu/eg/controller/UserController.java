package com.ncepu.eg.controller;

import com.ncepu.eg.pojo.Admin;
import com.ncepu.eg.pojo.PageBean;
import com.ncepu.eg.pojo.Result;
import com.ncepu.eg.pojo.User;
import com.ncepu.eg.service.UserService;
import com.ncepu.eg.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @author zwy
 * @version 1.0
 * @description: TODO
 * @date 2023/12/20 0:23
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/userInfo")
    public Result<Admin> userInfo(/*@RequestHeader(name = "Authorization") String token*/) {
        //根据用户名查询用户
       /* Map<String, Object> map = JwtUtil.parseToken(token);
        String username = (String) map.get("username");*/
        Map<String, Object> map = ThreadLocalUtil.get();
        String username = (String) map.get("username");
        Admin user = userService.findByUserName(username);
        return Result.success(user);
    }


    @GetMapping
    public Result<PageBean<User>> list(Integer pageNum, Integer pageSize, @RequestParam(required = false) String nickName) {
        PageBean<User> pbg = userService.list(pageNum, pageSize, nickName);
        return Result.success(pbg);
    }

    @GetMapping("/getOne")
    public Result<User> getOne(String nickName) {
        User user = userService.getOne(nickName);
        return Result.success(user);
    }

    @DeleteMapping("/delete")
    public Result delete(Integer id) {
        userService.delete(id);
        return Result.success();
    }


}
