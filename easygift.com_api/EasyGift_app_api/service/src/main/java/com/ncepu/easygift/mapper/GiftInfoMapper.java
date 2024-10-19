package com.ncepu.easygift.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.yulichang.base.MPJBaseMapper;
import com.ncepu.easygift.pojo.GiftInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface GiftInfoMapper extends MPJBaseMapper<GiftInfo> {
    Page<GiftInfo> getGiftList(Page<GiftInfo> page,
                               @Param(Constants.WRAPPER) QueryWrapper<GiftInfo> giftInfoWrapper,
                               @Param("communityId") Long communityId);
}




