package com.ncepu.eg.controller;

import com.ncepu.eg.pojo.Admin;
import com.ncepu.eg.pojo.Result;
import com.ncepu.eg.service.AdminService;
import com.ncepu.eg.utils.JwtUtil;
import com.ncepu.eg.utils.Md5Util;
import jakarta.validation.constraints.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author zwy
 * @version 1.0
 * @description: TODO
 * @date 2023/12/16 0:37
 */
@RestController
@RequestMapping
public class AdminController {

    @Autowired
    private AdminService adminService;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @PostMapping("/login")
    public Result<String> login(@Pattern(regexp = "^\\S{5,16}$") String username, @Pattern(regexp = "^\\S{5,16}$") String password) {
        //根据用户名查询用户
        Admin loginAdmin = adminService.findByAdminAccount(username);
        //判断该用户是否存在
        if (username == null) {
            return Result.error("用户名错误");
        }
        String psd = Md5Util.getMD5String(password);
        //判断密码是否正确  loginUser对象中的password是密文
        if (psd.equals(loginAdmin.getPassword())) {
            //登录成功
            Map<String, Object> claims = new HashMap<>();
            claims.put("username", loginAdmin.getUserName());
            claims.put("password", loginAdmin.getPassword());
            String token = JwtUtil.genToken(claims);
            //把token存储到redis中
            ValueOperations<String, String> operations = stringRedisTemplate.opsForValue();
            operations.set(token,token,1, TimeUnit.HOURS);
            return Result.success(token);
        }
        return Result.error("密码错误");
    }

    @PatchMapping("updateAvatar")
    public Result updateAvatar(@RequestBody Admin admin) {
//        String img = "https://easygift.oss-cn-beijing.aliyuncs.com/"+admin.getUserPic();
//        admin.setUserPic(img);
        adminService.updateAvatar(admin);
        return Result.success();
    }

    @PostMapping("/resetPassword")
    public Result update(@RequestBody Admin admin) {
        adminService.update(admin);
        return Result.success();
    }


}
