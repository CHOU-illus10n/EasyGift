package com.ncepu.easygift.service.impl;

import com.github.yulichang.toolkit.JoinWrappers;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import com.ncepu.easygift.mapper.GoodMapper;
import com.ncepu.easygift.mapper.GoodOrderMapper;
import com.ncepu.easygift.mapper.UserMapper;
import com.ncepu.easygift.pojo.GiftImage;
import com.ncepu.easygift.pojo.GiftInfo;
import com.ncepu.easygift.pojo.GoodInfo;
import com.ncepu.easygift.service.GoodService;
import com.ncepu.easygift.vo.GiftListVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author zwy
 * @version 1.0
 * @description: TODO
 * @date 2023/12/27 18:20
 */
@Service
public class GoodServiceImpl implements GoodService {
    @Autowired
    private GoodMapper goodMapper;
@Autowired
private UserMapper userMapper;
@Autowired
private GoodOrderMapper goodOrderMapper;
    @Override
    public List<GoodInfo> list(Integer page) {
        int offset = (page - 1) * 20;  // 计算偏移量
        MPJLambdaWrapper wrapper = JoinWrappers.lambda(GoodInfo.class)
                .selectAll(GoodInfo.class)
                .eq(GoodInfo::getIsDeleted,0)
                .orderByDesc("create_time")
                .last("limit " + offset + "," + 20);
        List<GoodInfo> lsg = goodMapper.selectJoinList(GoodInfo.class,wrapper);
        return lsg;
    }

    @Override
    public List<GoodInfo> getGoodListByCategory(Long categoryId, int page) {
        int offset = (page - 1) * 20;  // 计算偏移量
        MPJLambdaWrapper wrapper = JoinWrappers.lambda(GoodInfo.class)
                .selectAll(GoodInfo.class)
                .eq(GoodInfo::getIsDeleted,0)
                .eq(GoodInfo::getGoodCategoryId,categoryId)
                .orderByDesc("create_time")
                .last("limit " + offset + "," + 20);
        List<GoodInfo> lsg = goodMapper.selectJoinList(GoodInfo.class,wrapper);
        return lsg;
    }

    @Override
    public List<GoodInfo> getDetailInfo(Long goodId) {
        MPJLambdaWrapper wrapper = JoinWrappers.lambda(GoodInfo.class)
                .selectAll(GoodInfo.class)
                .eq(GoodInfo::getIsDeleted,0)
                .eq(GoodInfo::getGoodId,goodId);
        List<GoodInfo> lsg = goodMapper.selectJoinList(GoodInfo.class,wrapper);
        return lsg;
    }

    @Override
    public void buyGood(Long goodId, Long userId,Integer goodCount) {
        goodMapper.buy(goodId,goodCount);
        Integer point=goodMapper.getPoint(goodId);
        Integer points = point*goodCount;
        userMapper.buy(userId,goodCount,points);
        goodOrderMapper.insertOrder(userId,goodId,goodCount,points);
    }
}
