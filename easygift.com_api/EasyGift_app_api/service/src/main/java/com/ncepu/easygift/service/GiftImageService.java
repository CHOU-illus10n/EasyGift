package com.ncepu.easygift.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ncepu.easygift.pojo.GiftImage;
import com.ncepu.easygift.vo.SwiperVo;

import java.util.List;

/**
 * @Author：zyf
 * @Package：com.ncepu.easygift.service
 * @Project：easygift_wxapp_back
 * @name：GiftImageService
 * @Date：2023/12/17 21:42
 * @Filename：GiftImageService
 */
public interface GiftImageService extends IService<GiftImage> {

    List<SwiperVo> getSwiperInfo(Long communityId);

    List<SwiperVo> getPublishInfoImg(Long giftId);
}
