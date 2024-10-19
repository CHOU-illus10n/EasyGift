package com.ncepu.easygift.service;

import com.ncepu.easygift.pojo.MessageInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ncepu.easygift.vo.MessageListVo;

import java.util.List;


public interface MessageInfoService extends IService<MessageInfo> {
    List<MessageListVo> submitMessage(MessageInfo messageInfo);

    List<MessageListVo> getMessageList(Long giftId);

//    List<MessageInfo> getEnrollInfo(String giftId);
//    List<MessageInfo> getMessageNotice(Long userId, String giftId);
}
