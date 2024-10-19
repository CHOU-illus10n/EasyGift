package com.ncepu.easygift.service.impl;

import com.github.yulichang.toolkit.JoinWrappers;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import com.ncepu.easygift.mapper.GoodOrderMapper;
import com.ncepu.easygift.pojo.GoodInfo;
import com.ncepu.easygift.pojo.GoodOrder;
import com.ncepu.easygift.service.GoodOrderService;
import com.ncepu.easygift.vo.GoodOrderVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author zwy
 * @version 1.0
 * @description: TODO
 * @date 2023/12/28 22:48
 */
@Service
public class GoodOrderServiceImpl implements GoodOrderService {

    @Autowired
    private GoodOrderMapper goodOrderMapper;

    @Override
    public List<GoodOrderVO> list(int page, int userId) {
        int offset = (page - 1) * 20;  // 计算偏移量
        MPJLambdaWrapper wrapper = JoinWrappers.lambda(GoodOrder.class)
                .selectAll(GoodOrder.class)
                .select(GoodInfo::getGoodName)
                .eq(GoodOrder::getIsDeleted,0)
                .leftJoin(GoodInfo.class,GoodInfo::getGoodId,GoodOrder::getGoodId)
                .orderByDesc("create_time")
                .eq(GoodOrder::getReceiverId,userId)
                .last("limit " + offset + "," + 20);
        List<GoodOrderVO> lsg = goodOrderMapper.selectJoinList(GoodOrderVO.class,wrapper);
        return lsg;
    }

    @Override
    public void change(Long orderId, int userId, Integer state) {

            goodOrderMapper.changeState(orderId,userId,state);



    }
}
