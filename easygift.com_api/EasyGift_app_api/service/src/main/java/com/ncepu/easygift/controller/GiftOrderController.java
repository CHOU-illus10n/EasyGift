package com.ncepu.easygift.controller;

import com.ncepu.easygift.exception.XzException;
import com.ncepu.easygift.pojo.GiftOrder;
import com.ncepu.easygift.result.R;
import com.ncepu.easygift.result.REnum;
import com.ncepu.easygift.service.GiftOrderService;
import com.ncepu.easygift.util.JwtHelper;
import com.ncepu.easygift.vo.IReceiveVO;
import com.ncepu.easygift.vo.ISenderVo;
import com.ncepu.easygift.vo.NoticeVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


@RestController
@RequestMapping("/easygift")
@CrossOrigin
public class GiftOrderController {
    @Autowired
    private GiftOrderService giftOrderService;



    @GetMapping("/ISend")
    public R getISend(HttpServletRequest request) {
        if (request.getHeader("token") == null)
            throw new XzException(REnum.LoginError.getCode(), "获取token信息失败");
        System.out.println(request.getHeader("token"));
        Long userId = JwtHelper.getUserId(request.getHeader("token"));
        List<ISenderVo> list=giftOrderService.getISend(userId);
        return R.ok().data("List", list);
    }
    @GetMapping("/NoticeList")
    public R getNoticeList(HttpServletRequest request) {
        if (request.getHeader("token") == null)
            throw new XzException(REnum.LoginError.getCode(), "获取token信息失败");
        System.out.println(request.getHeader("token"));
        Long userId = JwtHelper.getUserId(request.getHeader("token"));
        List<NoticeVo> list=giftOrderService.getNoticeList(userId);
        return R.ok().data("noticeList", list);
    }

    @PostMapping("/send")
    public R send(HttpServletRequest request,
                  @RequestBody GiftOrder giftOrder) {
        if (request.getHeader("token") == null)
            throw new XzException(REnum.LoginError.getCode(), "获取token信息失败");
        giftOrderService.send(giftOrder);
        System.out.println(giftOrder);
        return R.ok().message("赠送成功");
    }
    @GetMapping("/IReceive")
    public R getIReceive(HttpServletRequest request) {
        if (request.getHeader("token") == null)
            throw new XzException(REnum.LoginError.getCode(), "获取token信息失败");
        System.out.println(request.getHeader("token"));
        Long userId = JwtHelper.getUserId(request.getHeader("token"));
        List<IReceiveVO> list=giftOrderService.getIReceive(userId);
        return R.ok().data("List", list);
    }
    @PostMapping("/GiftState")
    public R changeGiftState(HttpServletRequest request,
                  @RequestBody GiftOrder giftOrder) {
        if (request.getHeader("token") == null)
            throw new XzException(REnum.LoginError.getCode(), "获取token信息失败");
        Long giftId=giftOrder.getGiftId();
        giftOrderService.changeGiftState(giftId);
        return R.ok().message("状态改变成功");
    }
    @PostMapping("/GiftStateReject")
    public R changeGiftStateReject(HttpServletRequest request,
                             @RequestBody GiftOrder giftOrder) {
        if (request.getHeader("token") == null)
            throw new XzException(REnum.LoginError.getCode(), "获取token信息失败");
        Long giftId=giftOrder.getGiftId();
        giftOrderService.changeGiftStateReject(giftId);
        return R.ok().message("状态改变成功");
    }
}
