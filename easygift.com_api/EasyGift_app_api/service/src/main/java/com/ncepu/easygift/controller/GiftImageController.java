package com.ncepu.easygift.controller;

import com.ncepu.easygift.exception.XzException;
import com.ncepu.easygift.result.REnum;
import com.ncepu.easygift.result.R;
import com.ncepu.easygift.service.GiftImageService;
import com.ncepu.easygift.service.GiftInfoService;
import com.ncepu.easygift.util.JwtHelper;
import com.ncepu.easygift.vo.SwiperVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


@RestController
@RequestMapping("/easygift")
@CrossOrigin
public class GiftImageController {

    @Autowired
    private GiftImageService giftImageService;

    @Autowired
    private GiftInfoService giftInfoService;

    @GetMapping("/swiper")
    public R GetSwiperList(HttpServletRequest request) {

        if (request.getHeader("token") != "") {
            Long communityId = JwtHelper.getCommunityId(request.getHeader("token"));
            List<SwiperVo> swiperList = giftImageService.getSwiperInfo(communityId);
            return R.ok().message("获取轮播图信息列表成功").data("swiper", swiperList);
        }else {
            Long communityId = 1L;
            List<SwiperVo> swiperList = giftImageService.getSwiperInfo(communityId);
            return R.ok().message("获取轮播图信息列表成功").data("swiper", swiperList);
        }


    }

    @GetMapping("/publishinfoImg")
    public R GetPublishInfoImg(HttpServletRequest request) {
        Long giftId = Long.valueOf(request.getHeader("giftId"));
        List<SwiperVo> publishInfoImgList = giftImageService.getPublishInfoImg(giftId);
        return R.ok().message("获取详情信息列表成功").data("list", publishInfoImgList);
    }
}
