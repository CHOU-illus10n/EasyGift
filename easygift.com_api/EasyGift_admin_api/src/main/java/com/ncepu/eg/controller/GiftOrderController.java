package com.ncepu.eg.controller;

import com.ncepu.eg.pojo.GiftOrder;
import com.ncepu.eg.pojo.PageBean;
import com.ncepu.eg.pojo.Result;
import com.ncepu.eg.service.GiftOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zwy
 * @version 1.0
 * @description: TODO
 * @date 2023/12/14 1:04
 */
@RestController
@RequestMapping("/gift/order")
public class GiftOrderController {

    @Autowired
    private GiftOrderService giftOrderService;

    @GetMapping
    public Result<PageBean<GiftOrder>> list(Integer pageNum, Integer pageSize) {
        PageBean<GiftOrder> pbg=  giftOrderService.list( pageNum,  pageSize);
        return Result.success(pbg);
    }

    @PostMapping("/delete")
    public Result delete(Integer orderId) {
        giftOrderService.delete(orderId);
        return Result.success();
    }
}
