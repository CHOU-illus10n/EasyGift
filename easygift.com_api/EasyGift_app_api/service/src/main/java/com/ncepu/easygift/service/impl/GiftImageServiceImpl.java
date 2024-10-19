package com.ncepu.easygift.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.yulichang.toolkit.JoinWrappers;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import com.ncepu.easygift.mapper.GiftImgMapper;
import com.ncepu.easygift.pojo.GiftImage;
import com.ncepu.easygift.pojo.GiftInfo;
import com.ncepu.easygift.service.GiftImageService;
import com.ncepu.easygift.service.GiftInfoService;
import com.ncepu.easygift.vo.SwiperVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author：zyf
 * @Package：com.ncepu.easygift.service.impl
 * @Project：easygift_wxapp_back
 * @name：GiftImageServiceImpl
 * @Date：2023/12/17 21:43
 * @Filename：GiftImageServiceImpl
 */
@Service
public class GiftImageServiceImpl extends ServiceImpl<GiftImgMapper, GiftImage> implements GiftImageService {

    @Autowired
    private GiftInfoService giftInfoService;

    @Autowired
    private GiftImgMapper giftImgMapper;

    @Override
    public List<SwiperVo> getSwiperInfo(Long communityId) {
        MPJLambdaWrapper<GiftImage> imageQueryWrapper = JoinWrappers.lambda(GiftImage.class)
                .selectAll(GiftImage.class)
                .select(GiftInfo::getCommunityId)
                .leftJoin(GiftInfo.class,GiftInfo::getGiftId,GiftImage::getGiftId)
                .eq(GiftInfo::getCommunityId,communityId)
                .last("limit 4");
                ;
        List<SwiperVo> swiperVoList = giftImgMapper.selectJoinList(SwiperVo.class,imageQueryWrapper);
        return swiperVoList;
    }

    @Override
    public List<SwiperVo> getPublishInfoImg(Long giftId) {
        MPJLambdaWrapper<GiftImage> imageQueryWrapper = JoinWrappers.lambda(GiftImage.class)
                .selectAll(GiftImage.class)
                .leftJoin(GiftInfo.class,GiftInfo::getGiftId,GiftImage::getGiftId)
                .eq(GiftInfo::getGiftId,giftId)
        ;
        List<SwiperVo> List = giftImgMapper.selectJoinList(SwiperVo.class,imageQueryWrapper);
        return List;
    }
}
