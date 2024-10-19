package com.ncepu.eg.controller;

import com.ncepu.eg.pojo.*;
import com.ncepu.eg.service.GiftService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author zwy
 * @version 1.0
 * @description: TODO
 * @date 2023/12/13 10:10
 */
@RestController
@RequestMapping("/gift")
@Validated
public class GiftController {
    @Autowired
    private GiftService giftService;


    @GetMapping
    public Result<PageBean<GiftVO>> list(
            Integer pageNum,
            Integer pageSize,
            @RequestParam(required = false) Integer categoryId,
            @RequestParam(required = false) Integer state
    ) {
        PageBean<GiftVO> pb =  giftService.list(pageNum,pageSize,categoryId,state);
        return Result.success(pb);
    }

    @GetMapping("/detail")
    public Result<PageBean<GiftInfo>> listDetail(Integer pageNum, Integer pageSize) {
        PageBean<GiftInfo> pbg=  giftService.listDetail( pageNum,  pageSize);
        return Result.success(pbg);
    }

    @GetMapping("/")
    public Result<List<GiftVO>> listAll() {
        List<GiftVO> pbg=  giftService.listAll();
        return Result.success(pbg);
    }
    @GetMapping("/one")
    public Result<GiftVO> getOne(Integer id) {
        GiftVO giftVO =  giftService.getOne(id);
        return Result.success(giftVO);
    }
    @PostMapping("/change")
    public Result changeState(@RequestBody GiftDTO giftInfo) {
        Integer id = giftInfo.getGiftId();
        Integer state = giftInfo.getState();
        giftService.changeState(id,state);
        return Result.success();
    }

    @PostMapping("/delete")
    public Result delete(Integer id) {
        giftService.delete(id);
        return Result.success();
    }

}
