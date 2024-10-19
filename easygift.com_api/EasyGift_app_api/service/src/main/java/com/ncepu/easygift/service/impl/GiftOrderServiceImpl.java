package com.ncepu.easygift.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.yulichang.toolkit.JoinWrappers;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import com.ncepu.easygift.mapper.GiftInfoMapper;
import com.ncepu.easygift.mapper.UserMapper;
import com.ncepu.easygift.pojo.GiftImage;
import com.ncepu.easygift.pojo.GiftInfo;
import com.ncepu.easygift.pojo.GiftOrder;
import com.ncepu.easygift.pojo.User;
import com.ncepu.easygift.service.GiftOrderService;
import com.ncepu.easygift.mapper.GiftOrderMapper;
import com.ncepu.easygift.vo.IReceiveVO;
import com.ncepu.easygift.vo.ISenderVo;
import com.ncepu.easygift.vo.NoticeVo;
import com.ncepu.easygift.vo.PublishVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;


@Service
public class GiftOrderServiceImpl extends ServiceImpl<GiftOrderMapper, GiftOrder>
        implements GiftOrderService {
    @Autowired
    GiftOrderMapper giftOrderMapper;
    @Autowired
    GiftInfoMapper giftInfoMapper;
    @Autowired
    UserMapper userMapper;

    @Override
    public void send(GiftOrder giftOrder) {
        //对订单表的操作
        giftOrder.setGiftOrderStatus(3);
        giftOrderMapper.insert(giftOrder);
        //对转增物品表的操作
        UpdateWrapper<GiftInfo> updateWrapper2 = new UpdateWrapper<>();
        updateWrapper2.eq("gift_id", giftOrder.getGiftId());
        updateWrapper2.set("state", 3);
        giftInfoMapper.update(null, updateWrapper2);
    }

    @Override
    public List<NoticeVo> getNoticeList(Long userId) {
        MPJLambdaWrapper<GiftOrder> wrapper = JoinWrappers.lambda(GiftOrder.class)
                .select(GiftOrder::getGiftId)
                .select(GiftOrder::getCreateTime)
                .select(GiftOrder::getReceiverId)
                .select(GiftOrder::getSenderId)
                .select(GiftOrder::getGiftOrderStatus)
                .selectAs("receiver", User::getNickName, NoticeVo::getReceiverName)
                .selectAs("sender", User::getNickName, NoticeVo::getSenderName)
                .select(GiftInfo::getGiftName)
                .leftJoin(User.class, "receiver", User::getUserId, GiftOrder::getReceiverId)
                .leftJoin(User.class, "sender", User::getUserId, GiftOrder::getSenderId)
                .leftJoin(GiftInfo.class, GiftInfo::getGiftId, GiftOrder::getGiftId)
                .orderByDesc("create_time");
        List<NoticeVo> list = giftOrderMapper.selectJoinList(NoticeVo.class, wrapper);
        for (int i = 0; i < list.size(); i++) {
            NoticeVo noticeVo = list.get(i);
            if (userId == noticeVo.getSenderId()) {
                if (noticeVo.getGiftOrderStatus() == 4) {
                    String detail = "您的转赠物品" + noticeVo.getGiftName()
                            + "已经送给" + noticeVo.getReceiverName() + "了，" + "请在约定时间等待Ta上门取货吧！";
                    noticeVo.setDetail(detail);
                } else if (noticeVo.getGiftOrderStatus() == -1) {
                    String detail = "您的转赠物品" + noticeVo.getGiftName()
                            + "被" + noticeVo.getReceiverName() + "拒绝了TT，" + "麻烦您再选择一个人赠送吧~";
                    noticeVo.setDetail(detail);
                }
            } else if (userId == noticeVo.getReceiverId()) {
                if (noticeVo.getGiftOrderStatus() == 3) {
                    String detail = noticeVo.getSenderName() + "已将" + "物品" + noticeVo.getGiftName()
                            + "送给了您！" + "请您确认收到哦~";
                    noticeVo.setDetail(detail);
                }
            }
        }
        for (int i = list.size() - 1; i >= 0; i--) {
            NoticeVo element = list.get(i);
            if (element.getDetail() == null) {
                list.remove(i);
            }
        }
        System.out.println(list);
        return list;
    }

    @Override
    public List<ISenderVo> getISend(Long userId) {
        MPJLambdaWrapper<GiftOrder> wrapper = JoinWrappers.lambda(GiftOrder.class)
                .selectAll(GiftOrder.class)
                .selectAs("receiver", User::getNickName, ISenderVo::getReceiverName)
                .selectAs("sender", User::getNickName, ISenderVo::getSenderName)
                .selectAs("receiver", User::getProfileUrl, ISenderVo::getProfileUrl)
                .select(GiftInfo::getGiftName,GiftInfo::getDealTime)
                .selectAs("receiver",User::getPhone, ISenderVo::getPhone)
                .selectAs(GiftImage::getGiftImgUrl,ISenderVo::getGiftImgUrl)
                .eq(GiftOrder::getSenderId,userId)
                .eq(GiftOrder::getGiftOrderStatus, 4)
                .leftJoin(User.class, "receiver", User::getUserId, GiftOrder::getReceiverId)
                .leftJoin(User.class, "sender", User::getUserId, GiftOrder::getSenderId)
                .leftJoin(GiftInfo.class, GiftInfo::getGiftId, GiftOrder::getGiftId)
                .leftJoin(GiftImage.class, GiftImage::getGiftId, GiftInfo::getGiftId)
                .groupBy("gift_id")
                .orderByDesc("create_time");
        List<ISenderVo> list = giftOrderMapper.selectJoinList(ISenderVo.class, wrapper);

        return list;
    }

    @Override
    public List<IReceiveVO> getIReceive(Long userId) {
        MPJLambdaWrapper<GiftOrder> wrapper1 = JoinWrappers.lambda(GiftOrder.class)
                .select(GiftOrder::getGiftId)
                .selectAs(GiftOrder::getGiftOrderStatus,IReceiveVO::getState)
                .select(GiftInfo::getDescription,GiftInfo::getGiftName,GiftInfo::getDealAddress,GiftInfo::getDealTime)
                .select(GiftImage::getGiftImgUrl)
                .select(User::getPhone)
                .eq(GiftOrder::getReceiverId,userId)
                .eq(GiftOrder::getGiftOrderStatus,3)
                .leftJoin(GiftInfo.class,GiftInfo::getGiftId,GiftOrder::getGiftId)
                .leftJoin(GiftImage.class,GiftImage::getGiftId,GiftInfo::getGiftId)
                .leftJoin(User.class,User::getUserId,GiftOrder::getSenderId)
                .groupBy("gift_id")
                .orderByDesc(GiftOrder::getCreateTime);
        List<IReceiveVO> list1 = giftOrderMapper.selectJoinList(IReceiveVO.class, wrapper1);
        MPJLambdaWrapper<GiftOrder> wrapper2 = JoinWrappers.lambda(GiftOrder.class)
                .select(GiftOrder::getGiftId)
                .selectAs(GiftOrder::getGiftOrderStatus,IReceiveVO::getState)
                .select(GiftInfo::getDescription,GiftInfo::getGiftName,GiftInfo::getDealAddress,GiftInfo::getDealTime)
                .select(GiftImage::getGiftImgUrl)
                .select(User::getPhone)
                .eq(GiftOrder::getReceiverId,userId)
                .eq(GiftOrder::getGiftOrderStatus,4)
                .leftJoin(GiftInfo.class,GiftInfo::getGiftId,GiftOrder::getGiftId)
                .leftJoin(GiftImage.class,GiftImage::getGiftId,GiftInfo::getGiftId)
                .leftJoin(User.class,User::getUserId,GiftOrder::getSenderId)
                .groupBy("gift_id")
                .orderByDesc(GiftOrder::getCreateTime);
        List<IReceiveVO> list2 = giftOrderMapper.selectJoinList(IReceiveVO.class, wrapper2);
        list1.addAll(list2);
        return list1;
    }

    @Override
    public void changeGiftState(Long giftId) {
        // 修改gift_order表特定giftId的state字段，改成4
        UpdateWrapper<GiftOrder> updateWrapper1 = new UpdateWrapper<>();
        updateWrapper1.eq("gift_id", giftId);
        updateWrapper1.set("gift_order_status", 4);
        giftOrderMapper.update(null, updateWrapper1);

        // 取出修改这一段的incr_point
        MPJLambdaWrapper<GiftOrder> wrapper = JoinWrappers.lambda(GiftOrder.class)
                .select(GiftOrder::getIncrPoint,GiftOrder::getSenderId)
                .eq(GiftOrder::getGiftId,giftId);
        List<GiftOrder> list = giftOrderMapper.selectJoinList(GiftOrder.class, wrapper);
        GiftOrder temp=list.get(0);
        // 加到用户表里的point里
        UpdateWrapper<User> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("user_id", temp.getSenderId());
        updateWrapper.setSql("points = points + " + temp.getIncrPoint());
        userMapper.update(null, updateWrapper);

        // 修改giftInfo里特定giftId的状态字段，把3改成4
        UpdateWrapper<GiftInfo> updateWrapper2 = new UpdateWrapper<>();
        updateWrapper2.eq("gift_id", giftId);
        updateWrapper2.set("state", 4);
        giftInfoMapper.update(null, updateWrapper2);
    }

    @Override
    public void changeGiftStateReject(Long giftId) {
        // 修改gift_order表特定giftId的state字段，改成-1
        UpdateWrapper<GiftOrder> updateWrapper1 = new UpdateWrapper<>();
        updateWrapper1.eq("gift_id", giftId);
        updateWrapper1.set("gift_order_status", -1);
        giftOrderMapper.update(null, updateWrapper1);


        // 修改giftInfo里特定giftId的状态字段，把3改成1
        UpdateWrapper<GiftInfo> updateWrapper2 = new UpdateWrapper<>();
        updateWrapper2.eq("gift_id", giftId);
        updateWrapper2.set("state", 1);
        giftInfoMapper.update(null, updateWrapper2);
    }
}




