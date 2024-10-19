package com.ncepu.easygift.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ncepu.easygift.exception.XzException;
import com.ncepu.easygift.pojo.GoodInfo;
import com.ncepu.easygift.pojo.PageBean;
import com.ncepu.easygift.pojo.PointMallInfo;
import com.ncepu.easygift.result.R;
import com.ncepu.easygift.result.REnum;
import com.ncepu.easygift.result.Result;
import com.ncepu.easygift.service.GoodService;
import com.ncepu.easygift.util.JwtHelper;
import com.ncepu.easygift.vo.GiftListVo;
import com.ncepu.easygift.vo.PublishVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;

/**
 * @author zwy
 * @version 1.0
 * @description: TODO
 * @date 2023/12/27 17:45
 */
@RestController
@RequestMapping("/pointmall/good")
@CrossOrigin
public class GoodController {
    @Autowired
    private GoodService goodService;


    @GetMapping("/list")
    public R GetGoodList(HttpServletRequest request) {
        int page= Integer.parseInt(request.getHeader("page"));
        List<GoodInfo> GiftList = goodService.list(page);
        return R.ok().message("获取商品信息成功").data("goodlist", GiftList);
    }

    @GetMapping("/bycategory")
    public R GetGoodListByCategory(HttpServletRequest request) {
        int page= Integer.parseInt(request.getHeader("page"));
        Long categoryId= Long.valueOf(request.getHeader("categoryId"));
        List<GoodInfo> GoodList = goodService.getGoodListByCategory(categoryId,page);
        return R.ok().message("通过类别获取商品信息成功").data("goodlist", GoodList);
    }

    @GetMapping("/detail")
    public R getDetailInfo(HttpServletRequest request){
        Long goodId= Long.valueOf(request.getHeader("goodId"));
        List<GoodInfo> goodInfo=goodService.getDetailInfo(goodId);
        return R.ok().message("返回物品详情页信息成功").data("goodInfo",goodInfo);
    }
    @PostMapping("/buy")
    public R buyGood(HttpServletRequest request){
        Long goodId= Long.valueOf(request.getHeader("goodId"));
        Long userId= Long.valueOf(request.getHeader("userId"));
        Integer goodCount = Integer.valueOf(request.getHeader("goodCount"));
        goodService.buyGood(goodId,userId,goodCount);

        return R.ok().message("购买成功");
    }

}
