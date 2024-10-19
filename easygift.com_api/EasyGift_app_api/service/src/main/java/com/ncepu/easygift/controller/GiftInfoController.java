package com.ncepu.easygift.controller;

import com.ncepu.easygift.exception.XzException;
import com.ncepu.easygift.result.R;
import com.ncepu.easygift.result.REnum;
import com.ncepu.easygift.service.GiftInfoService;
import com.ncepu.easygift.util.JwtHelper;
import com.ncepu.easygift.vo.GiftListVo;
import com.ncepu.easygift.vo.PublishVo;
import com.ncepu.easygift.vo.UploadGiftVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;


@RestController
@RequestMapping("/easygift")
@CrossOrigin
public class GiftInfoController {

    @Autowired
    private GiftInfoService giftInfoService;

    @GetMapping("/search")
    public R SearchGiftList(HttpServletRequest request) {
        String encodeSearchText = request.getHeader("searchText");
        if (request.getHeader("token") == null) {
            try {
                Long communityId = JwtHelper.getCommunityId(request.getHeader("token"));
                System.out.println(encodeSearchText);
                String searchText = URLDecoder.decode(encodeSearchText, "UTF-8");
                List<PublishVo> list = giftInfoService.searchGiftList(searchText, communityId);
                return R.ok().message("返回物品搜索信息成功").data("list", list);
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException(e);
            }
        } else {
            try {
                Long communityId = 1L;
                System.out.println(encodeSearchText);
                String searchText = URLDecoder.decode(encodeSearchText, "UTF-8");
                List<PublishVo> list = giftInfoService.searchGiftList(searchText, communityId);
                return R.ok().message("返回物品搜索信息成功").data("list", list);
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException(e);
            }
        }

    }

    @GetMapping("/publishinfo")
    public R getPublishInfo(HttpServletRequest request) {
        Long giftId = Long.valueOf(request.getHeader("giftId"));
        List<PublishVo> publishVoList = giftInfoService.getPublishInfo(giftId);
        return R.ok().message("返回物品详情页信息成功").data("publishVoList", publishVoList);
    }

    @GetMapping("/giftlist/bycategory")
    public R GetGiftListByCategory(HttpServletRequest request) {
        if (request.getHeader("token") != "") {
            Long communityId = JwtHelper.getCommunityId(request.getHeader("token"));
            if (request.getHeader("categoryId") == "")
                throw new XzException(REnum.LoginError.getCode(), "获取categoryId失败");
            int page = Integer.parseInt(request.getHeader("page"));
            Long categoryId = Long.valueOf(request.getHeader("categoryId"));
            List<GiftListVo> GiftList = giftInfoService.getGiftlistByCategory(communityId, categoryId, page);
            return R.ok().message("通过类别获取物品信息成功").data("giftlist", GiftList);
        } else {
            Long communityId = 1L;
            if (request.getHeader("categoryId") == "")
                throw new XzException(REnum.LoginError.getCode(), "获取categoryId失败");
            int page = Integer.parseInt(request.getHeader("page"));
            Long categoryId = Long.valueOf(request.getHeader("categoryId"));
            List<GiftListVo> GiftList = giftInfoService.getGiftlistByCategory(communityId, categoryId, page);
            return R.ok().message("通过类别获取物品信息成功").data("giftlist", GiftList);
        }

    }

    @GetMapping("/IPublish")
    public R GetIPublish(HttpServletRequest request) {
        System.out.println(request.getHeader("token"));
        Long userId = JwtHelper.getUserId(request.getHeader("token"));;
        List<GiftListVo> GiftList = giftInfoService.getIPublish(userId);
        return R.ok().message("获取物品信息成功").data("giftlist", GiftList);
    }

    @GetMapping("/giftlist")
    public R GetGiftList(HttpServletRequest request) {
        if (request.getHeader("token") != "") {
            System.out.println(request.getHeader("token"));
            Long communityId = JwtHelper.getCommunityId(request.getHeader("token"));
            int page = Integer.parseInt(request.getHeader("page"));
            List<GiftListVo> GiftList = giftInfoService.getGiftlist(communityId, page);
            return R.ok().message("获取物品信息成功").data("giftlist", GiftList);
        } else {
            Long communityId = 1L;
            int page = Integer.parseInt(request.getHeader("page"));
            List<GiftListVo> GiftList = giftInfoService.getGiftlist(communityId, page);
            return R.ok().message("获取物品信息成功").data("giftlist", GiftList);
        }

    }

    @PostMapping("/submit")
    public R submitGiftInfo(HttpServletRequest request, @RequestBody UploadGiftVo vo) {
        if (request.getHeader("token") == null)
            throw new XzException(REnum.LoginError.getCode(), "获取token失败");
        System.out.println(request.getHeader("token"));
        Long userId = JwtHelper.getUserId(request.getHeader("token"));
        Long communityId = JwtHelper.getCommunityId(request.getHeader("token"));
        Long giftId = giftInfoService.submitGiftInfo(userId, communityId, vo);
        return R.ok().message("物品提交成功").data("giftId", giftId);
    }

}
