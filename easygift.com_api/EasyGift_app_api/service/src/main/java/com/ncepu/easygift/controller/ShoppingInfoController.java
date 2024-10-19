package com.ncepu.easygift.controller;

import com.ncepu.easygift.pojo.ShoppingInfo;
import com.ncepu.easygift.result.R;
import com.ncepu.easygift.service.ShoppingInfoService;
import com.ncepu.easygift.util.JwtHelper;
import com.ncepu.easygift.vo.GoodOrderVO;
import com.ncepu.easygift.vo.ShoppingInfoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author zwy
 * @version 1.0
 * @description: TODO
 * @date 2023/12/29 20:45
 */
@RestController
@RequestMapping("/user/shopping")
@CrossOrigin
public class ShoppingInfoController {

    @Autowired
    private ShoppingInfoService shoppingInfoService;
    @PostMapping("/address")
    public R addAddress(HttpServletRequest request, @RequestBody ShoppingInfo shoppingInfo) {
        String token = request.getHeader("token");
        Long userId = JwtHelper.getUserId(token);
        System.out.println(userId);
        String receiverName = shoppingInfo.getReceiverName();
        String phone = shoppingInfo.getReceiverPhone();
        String address = shoppingInfo.getAddress();
        String receiverAddress = shoppingInfo.getReceiverAddress();
        int state = shoppingInfo.getState();
        shoppingInfoService.addAddress(receiverName,phone,userId,address,receiverAddress,state);
        return R.ok().message("添加地址成功");
    }

    @GetMapping("/list")
    public R GetShoppingList(HttpServletRequest request) {
        int page= Integer.parseInt(request.getHeader("page"));
        int userId= Integer.parseInt(request.getHeader("userId"));
        List<ShoppingInfoVO> shoppingInfos = shoppingInfoService.list(page,userId);
        return R.ok().message("获取用户地址信息成功").data("shoppingInfo", shoppingInfos);
    }

    @PostMapping("/update")
    public R update(Long userId,Long shoppingId) {
        shoppingInfoService.update(userId,shoppingId);
        return R.ok().message("修改默认地址成功");
    }
}
