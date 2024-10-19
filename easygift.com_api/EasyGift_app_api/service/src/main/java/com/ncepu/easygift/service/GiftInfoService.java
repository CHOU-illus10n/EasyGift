package com.ncepu.easygift.service;

import com.ncepu.easygift.pojo.GiftInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ncepu.easygift.vo.GiftListVo;
import com.ncepu.easygift.vo.PublishVo;
import com.ncepu.easygift.vo.UploadGiftVo;

import java.util.List;

public interface GiftInfoService extends IService<GiftInfo> {




    List<GiftListVo> getGiftlist(Long communityId, int page);

    Long submitGiftInfo(Long userId, Long communityId,UploadGiftVo vo);

    List<GiftListVo> getGiftlistByCategory(Long communityId, Long categoryId,int page);

    List<PublishVo> getPublishInfo(Long giftId);

    List<PublishVo> searchGiftList(String searchText, Long communityId);

    List<GiftListVo> getIPublish(Long userId);
}

