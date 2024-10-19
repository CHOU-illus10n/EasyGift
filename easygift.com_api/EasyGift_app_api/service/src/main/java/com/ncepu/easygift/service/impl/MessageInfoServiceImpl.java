package com.ncepu.easygift.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.yulichang.base.MPJBaseMapper;
import com.github.yulichang.toolkit.JoinWrappers;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import com.ncepu.easygift.exception.XzException;
import com.ncepu.easygift.pojo.GiftInfo;
import com.ncepu.easygift.pojo.MessageInfo;
import com.ncepu.easygift.pojo.User;
import com.ncepu.easygift.result.REnum;
import com.ncepu.easygift.service.GiftInfoService;
import com.ncepu.easygift.service.MessageInfoService;
import com.ncepu.easygift.mapper.MessageInfoMapper;
import com.ncepu.easygift.service.UserInfoService;
import com.ncepu.easygift.vo.GiftListVo;
import com.ncepu.easygift.vo.MessageListVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;
import java.util.Map;


@Service
public class MessageInfoServiceImpl extends ServiceImpl<MessageInfoMapper, MessageInfo>
    implements MessageInfoService{
    @Autowired
    private MessageInfoMapper messageInfoMapper;
    @Override
    public List<MessageListVo> submitMessage(MessageInfo messageInfo) {
        System.out.println(messageInfo);
        messageInfoMapper.insert(messageInfo);
        MPJLambdaWrapper wrapper = JoinWrappers.lambda(MessageInfo.class)
                .selectAll(MessageInfo.class)
                .selectAll(User.class)
                .leftJoin(User.class,User::getUserId,MessageInfo::getUserId)
                .eq(MessageInfo::getGiftId,messageInfo.getGiftId());;
        List<MessageListVo> list = messageInfoMapper.selectJoinList(MessageListVo.class,wrapper);
        return list;
    }

    @Override
    public List<MessageListVo> getMessageList(Long giftId) {
        MPJLambdaWrapper wrapper = JoinWrappers.lambda(MessageInfo.class)
                .selectAll(MessageInfo.class)
                .selectAll(User.class)
                .leftJoin(User.class,User::getUserId,MessageInfo::getUserId)
                .eq(MessageInfo::getGiftId,giftId);
        List<MessageListVo> list = messageInfoMapper.selectJoinList(MessageListVo.class,wrapper);
        return list;
    }

//    @Autowired
//    private UserInfoService userInfoService;
//    @Autowired
//    private GiftInfoService giftInfoService;
//
//    private void packageMessageInfo(MessageInfo messageInfo){
//        Map<String, Object> paramString  = messageInfo.getParamString();
//        User user = userInfoService.getById(messageInfo.getUserId());
//        paramString.put("userId", user.getUserId());
//        paramString.put("nickName", user.getNickName());
//        paramString.put("faceUrl", user.getProfileUrl());
//    }
//
//    @Override
//    public List<MessageInfo> getEnrollInfo(String giftId) {
//        QueryWrapper<MessageInfo> queryWrapper = new QueryWrapper<>();
//        queryWrapper.eq("gift_id", giftId);
//        queryWrapper.orderByDesc("create_time");
//        List<MessageInfo> messageInfos = baseMapper.selectList(queryWrapper);
//        messageInfos.forEach(this::packageMessageInfo);
//        return messageInfos;
//    }
//
//    @Override
//    public List<MessageInfo> getMessageNotice(Long userId, String giftId) {
//        GiftInfo g = giftInfoService.getById(giftId);
//        if (!g.getUserId().equals(userId))
//            throw new XzException(REnum.AuthorityError.getCode(), "用户无法获取他人物品留言通知");
//        QueryWrapper<MessageInfo> queryWrapper = new QueryWrapper<>();
//        queryWrapper.eq("gift_id", giftId);
//        List<MessageInfo> messageInfos = baseMapper.selectList(queryWrapper);
//        messageInfos.forEach(messageInfo -> {
//            this.completeNoticRes(messageInfo, g.getGiftName());
//        });
//        return messageInfos;
//    }
//
//    public void completeNoticRes(MessageInfo messageInfo, String giftName){
//        User u = userInfoService.getById(messageInfo.getUserId());
//        Map<String, Object> paramString = messageInfo.getParamString();
//        paramString.put("nickName", u.getNickName());
//        paramString.put("giftName", giftName);
//    }
}




