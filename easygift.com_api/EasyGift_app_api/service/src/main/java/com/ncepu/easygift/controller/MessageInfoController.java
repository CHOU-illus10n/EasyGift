package com.ncepu.easygift.controller;

import com.ncepu.easygift.exception.XzException;
import com.ncepu.easygift.pojo.MessageInfo;
import com.ncepu.easygift.result.R;
import com.ncepu.easygift.result.REnum;
import com.ncepu.easygift.service.MessageInfoService;
import com.ncepu.easygift.util.JwtHelper;
import com.ncepu.easygift.vo.MessageListVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


@RestController
@RequestMapping("/easygift")
@CrossOrigin
public class MessageInfoController {
    @Autowired
    private MessageInfoService messageInfoService;


    @PostMapping("/submitMessage")
    public R submitMessage(HttpServletRequest request, @RequestBody MessageInfo messageInfo) {
        if (request.getHeader("token") == null)
            throw new XzException(REnum.LoginError.getCode(), "获取token失败");
        Long userId = JwtHelper.getUserId(request.getHeader("token"));
        if (messageInfo.getContent() == null || messageInfo.getGiftId() == null)
            throw new XzException(REnum.ParamError.getCode(), "获取相关参数失败");
        messageInfo.setUserId(userId);
        List<MessageListVo> messageInfoList;
        messageInfoList=messageInfoService.submitMessage(messageInfo);
        return R.ok().message("留言成功").data("messageList",messageInfoList);
    }
    @GetMapping ("/getMessageList")
    public R getMessageList(HttpServletRequest request) {
        if (request.getHeader("token") == null)
            throw new XzException(REnum.LoginError.getCode(), "获取token失败");
        Long giftId= Long.valueOf(request.getHeader("giftId"));
        System.out.println(giftId);
        List<MessageListVo> messageInfoList;
        messageInfoList=messageInfoService.getMessageList(giftId);
        return R.ok().message("返回留言列表成功").data("messageList",messageInfoList);
    }
}
