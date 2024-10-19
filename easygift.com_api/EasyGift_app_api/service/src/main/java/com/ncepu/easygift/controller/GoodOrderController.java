package com.ncepu.easygift.controller;

import com.ncepu.easygift.pojo.GoodInfo;
import com.ncepu.easygift.pojo.GoodOrder;
import com.ncepu.easygift.result.R;
import com.ncepu.easygift.service.GoodOrderService;
import com.ncepu.easygift.vo.GoodOrderVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author zwy
 * @version 1.0
 * @description: TODO
 * @date 2023/12/28 22:44
 */
@RestController
@RequestMapping("/user/good/order")
@CrossOrigin
public class GoodOrderController {
    @Autowired
    private GoodOrderService goodOrderService;

    @GetMapping("/list")
    public R GetGoodOrderList(HttpServletRequest request) {
        int page= Integer.parseInt(request.getHeader("page"));
        int userId= Integer.parseInt(request.getHeader("userId"));
        List<GoodOrderVO> goodOrder = goodOrderService.list(page,userId);
        return R.ok().message("获取商品兑换信息成功").data("goodOrder", goodOrder);
    }
    @PostMapping("/change")
    public R ChangeGoodOrderState(HttpServletRequest request,
                                  @RequestBody GoodOrder goodOrder){
        int userId= Integer.parseInt(request.getHeader("userId"));
        Long orderId = Long.valueOf(goodOrder.getOrderId());
        Integer state = goodOrder.getState();
        goodOrderService.change(orderId,userId,state);
        return R.ok().message("修改商品兑换信息成功");
    }
}
