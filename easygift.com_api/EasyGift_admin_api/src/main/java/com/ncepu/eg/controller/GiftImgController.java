package com.ncepu.eg.controller;

import com.ncepu.eg.pojo.GiftImgVO;
import com.ncepu.eg.pojo.Result;
import com.ncepu.eg.service.GiftImgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author zwy
 * @version 1.0
 * @description: TODO
 * @date 2023/12/14 19:06
 */
@RestController
@RequestMapping("/gift")
public class GiftImgController {
    @Autowired
    private GiftImgService giftImgService;

    @GetMapping("/img")
    public Result<List<GiftImgVO>> list(Integer giftId) {
        List<GiftImgVO> imgs = giftImgService.list(giftId);
        return Result.success(imgs);
    }
}
