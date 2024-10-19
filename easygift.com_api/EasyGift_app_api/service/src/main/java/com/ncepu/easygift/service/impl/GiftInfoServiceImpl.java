package com.ncepu.easygift.service.impl;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.yulichang.toolkit.JoinWrappers;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import com.ncepu.easygift.mapper.GiftImgMapper;
import com.ncepu.easygift.mapper.UserMapper;
import com.ncepu.easygift.pojo.*;
import com.ncepu.easygift.pojo.GiftCategory;
import com.ncepu.easygift.service.*;
import com.ncepu.easygift.mapper.GiftInfoMapper;
import com.ncepu.easygift.vo.GiftListVo;
import com.ncepu.easygift.vo.PublishVo;
import com.ncepu.easygift.vo.UploadGiftVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;


@Service
public class GiftInfoServiceImpl extends ServiceImpl<GiftInfoMapper, GiftInfo>
    implements GiftInfoService{

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private GiftInfoMapper giftInfoMapper;

    @Autowired
    private GiftImgMapper giftImgMapper;

    private UserMapper userMapper;

    @Autowired
    private GiftCategoryService giftCategoryService;

    @Autowired
    private ObsService obsService;



    @Override
    public List<GiftListVo> getGiftlist(Long communityId, int page) {
        int offset = (page - 1) * 20;  // 计算偏移量
        MPJLambdaWrapper wrapper = JoinWrappers.lambda(GiftInfo.class)
                .selectAll(GiftInfo.class)
                .select(GiftImage::getGiftImgUrl)
                .leftJoin(GiftImage.class,GiftImage::getGiftId,GiftInfo::getGiftId)
                .in(GiftInfo::getState,0,1,3)
                .eq(GiftInfo::getCommunityId,communityId)
                .groupBy("gift_id")
                .orderByDesc("create_time")
                .last("limit " + offset + "," + 20);
        List<GiftListVo> giftListVoList = giftInfoMapper.selectJoinList(GiftListVo.class,wrapper);
        return giftListVoList;
    }

    @Override
    public Long submitGiftInfo(Long userId, Long communityId,UploadGiftVo vo) {
        // 插入礼物信息
        GiftInfo giftInfo = new GiftInfo();
        giftInfo.setUserId(userId);
        giftInfo.setCommunityId(communityId);
        giftInfo.setGiftName(vo.getGiftName());
        giftInfo.setDescription(vo.getDescription());
        giftInfo.setCategoryId(vo.getGiftTypeId());
        giftInfo.setGiftOriginPrice(Double.valueOf(vo.getGiftPrice()));
        giftInfo.setPurchaseTime(Date.valueOf(vo.getPurchaseTime()));
        giftInfo.setDealTime(Date.valueOf(vo.getDealTime()));
        giftInfo.setGiftQuality(Integer.valueOf(vo.getGiftQuality()));
        giftInfo.setDealAddress(vo.getDealAddress());
        giftInfo.setState(0);  // 默认成功发布

        giftInfoMapper.insert(giftInfo);
        Long giftId = giftInfo.getGiftId(); // 获取刚插入的礼物信息的ID

        // 插入礼物图片信息
        List<String> imgUrls = vo.getGiftImgUrls();
        for (String imgUrl : imgUrls) {
            GiftImage giftImage = new GiftImage();
            giftImage.setUserId(userId);
            giftImage.setGiftId(giftId);  // 获取刚插入的礼物信息的ID
            giftImage.setGiftImgUrl(imgUrl);
            giftImgMapper.insert(giftImage);
        }
        return giftId;
    }

    @Override
    public List<GiftListVo> getGiftlistByCategory(Long communityId, Long categoryId,int page) {
        int offset = (page - 1) * 20;  // 计算偏移量
        MPJLambdaWrapper wrapper = JoinWrappers.lambda(GiftInfo.class)
                .selectAll(GiftInfo.class)
                .select(GiftImage::getGiftImgUrl)
                .leftJoin(GiftImage.class,GiftImage::getGiftId,GiftInfo::getGiftId)
                .in(GiftInfo::getState,0,1,3)
                .eq(GiftInfo::getCategoryId,categoryId)
                .eq(GiftInfo::getCommunityId,communityId)
                .groupBy("gift_id")
                .orderByDesc("create_time")
                .last("limit " + offset + "," + 20);
        List<GiftListVo> giftListVoList = giftInfoMapper.selectJoinList(GiftListVo.class,wrapper);
        return giftListVoList;
    }

    @Override
    public List<PublishVo> getPublishInfo(Long giftId) {
        MPJLambdaWrapper<GiftInfo> wrapper = JoinWrappers.lambda(GiftInfo.class)
                .select(GiftInfo::getGiftId,GiftInfo::getCreateTime,GiftInfo::getDescription,
                        GiftInfo::getState,GiftInfo::getDescription,GiftInfo::getDealAddress,GiftInfo::getGiftName)
                .select(User::getUserId, User::getProfileUrl, User::getNickName, User::getOpenId)
                .select(GiftCategory::getCategoryId,GiftCategory::getCategoryName)
                .select(CommunityInfo::getCommunityId,CommunityInfo::getCommunityName)
                .select(GiftInfo::getGiftQuality,GiftInfo::getGiftOriginPrice,GiftInfo::getPurchaseTime)
                .leftJoin(User.class, User::getUserId, GiftInfo::getUserId)
                .leftJoin(GiftCategory.class, GiftCategory::getCategoryId, GiftInfo::getCategoryId)
                .leftJoin(CommunityInfo.class,CommunityInfo::getCommunityId,GiftInfo::getCommunityId)
                .eq(GiftInfo::getGiftId, giftId);

        List<PublishVo> publishVoList = giftInfoMapper.selectJoinList(PublishVo.class, wrapper);
        return publishVoList;
    }

    @Override
    public List<PublishVo> searchGiftList(String searchText, Long communityId) {
        MPJLambdaWrapper<GiftInfo> wrapper = JoinWrappers.lambda(GiftInfo.class)
                .select(GiftInfo::getGiftId,GiftInfo::getCreateTime,GiftInfo::getDescription,
                        GiftInfo::getState,GiftInfo::getDescription,GiftInfo::getDealAddress,GiftInfo::getGiftName)
                .select(User::getUserId, User::getProfileUrl, User::getNickName)
                .select(GiftCategory::getCategoryId,GiftCategory::getCategoryName)
                .select(CommunityInfo::getCommunityId,CommunityInfo::getCommunityName)
                .select(GiftImage::getGiftImgUrl)
                .leftJoin(User.class, User::getUserId, GiftInfo::getUserId)
                .leftJoin(GiftCategory.class, GiftCategory::getCategoryId, GiftInfo::getCategoryId)
                .leftJoin(CommunityInfo.class,CommunityInfo::getCommunityId,GiftInfo::getCommunityId)
                .leftJoin(GiftImage.class,GiftImage::getGiftId,GiftInfo::getGiftId)
                .eq(GiftInfo::getCommunityId,communityId)
                .like(StringUtils.isNotBlank(searchText), GiftInfo::getGiftName, searchText) // 在这里实现模糊搜索
                .or()
                .like(StringUtils.isNotBlank(searchText), GiftInfo::getDescription, searchText)
                .groupBy("gift_id");
        List<PublishVo> publishVoList = giftInfoMapper.selectJoinList(PublishVo.class, wrapper);
        return publishVoList;
    }

    @Override
    public List<GiftListVo> getIPublish(Long userId) {
        MPJLambdaWrapper wrapper = JoinWrappers.lambda(GiftInfo.class)
                .selectAll(GiftInfo.class)
                .select(GiftImage::getGiftImgUrl)
                .leftJoin(GiftImage.class,GiftImage::getGiftId,GiftInfo::getGiftId)
                .in(GiftInfo::getState,0,1,3)
                .eq(GiftInfo::getUserId,userId)
                .groupBy("gift_id")
                .orderByDesc("create_time");
        List<GiftListVo> giftListVoList = giftInfoMapper.selectJoinList(GiftListVo.class,wrapper);
        return giftListVoList;
    }

}




