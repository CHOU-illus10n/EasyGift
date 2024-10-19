package com.ncepu.easygift.service;

import com.ncepu.easygift.pojo.GiftOrder;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ncepu.easygift.vo.IReceiveVO;
import com.ncepu.easygift.vo.ISenderVo;
import com.ncepu.easygift.vo.NoticeVo;

import java.util.List;


public interface GiftOrderService extends IService<GiftOrder> {
    void send(GiftOrder giftOrder);

    List<NoticeVo> getNoticeList(Long userId);

    List<ISenderVo> getISend(Long userId);

    List<IReceiveVO> getIReceive(Long userId);


    void changeGiftState(Long giftId);

    void changeGiftStateReject(Long giftId);
}
