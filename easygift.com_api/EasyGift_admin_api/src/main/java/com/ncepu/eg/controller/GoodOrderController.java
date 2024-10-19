package com.ncepu.eg.controller;

import com.ncepu.eg.pojo.GoodOrder;
import com.ncepu.eg.pojo.PageBean;
import com.ncepu.eg.pojo.Result;
import com.ncepu.eg.service.GoodOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author zwy
 * @version 1.0
 * @description: TODO
 * @date 2023/12/15 10:10
 */
@RestController
@RequestMapping("/good/order")
public class GoodOrderController {

    @Autowired
    private GoodOrderService goodOrderService;


    @GetMapping
    public Result<PageBean<GoodOrder>> list(Integer pageNum, Integer pageSize,
                                            @RequestParam(required = false) Integer orderId) {
        PageBean<GoodOrder> pbg=  goodOrderService.list( pageNum,  pageSize,orderId);
        return Result.success(pbg);
    }

    @PostMapping("/send")
    public Result send(@RequestBody GoodOrder goodOrder) {
        goodOrderService.send(goodOrder);
        return Result.success();
    }
}
