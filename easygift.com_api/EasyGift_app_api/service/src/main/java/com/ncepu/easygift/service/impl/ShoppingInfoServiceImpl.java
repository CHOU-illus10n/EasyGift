package com.ncepu.easygift.service.impl;

import com.github.yulichang.toolkit.JoinWrappers;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import com.ncepu.easygift.mapper.ShoppingInfoMapper;
import com.ncepu.easygift.pojo.GoodInfo;
import com.ncepu.easygift.pojo.GoodOrder;
import com.ncepu.easygift.pojo.ShoppingInfo;
import com.ncepu.easygift.service.ShoppingInfoService;
import com.ncepu.easygift.vo.GoodOrderVO;
import com.ncepu.easygift.vo.ShoppingInfoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author zwy
 * @version 1.0
 * @description: TODO
 * @date 2023/12/29 20:47
 */
@Service
public class ShoppingInfoServiceImpl implements ShoppingInfoService {

    @Autowired
    private ShoppingInfoMapper shoppingInfoMapper;
    @Override
    public void addAddress(String receiverName, String phone, Long userId, String address,String receiverAddress,int state) {
        int num = shoppingInfoMapper.count(userId);
        if(num == 0){
            state = 1;
        }
        String[] add = address.split("-");
        String receiverProvince = add[0];
        String receiverCity = add[1];
        String receiverDistrict = add[2];
        if(state ==1){
            shoppingInfoMapper.updateState(userId);
        }
        shoppingInfoMapper.addAddress(receiverName,phone,userId,receiverProvince,receiverCity,receiverDistrict,receiverAddress,state);
    }

    @Override
    public List<ShoppingInfoVO> list(int page, int userId) {
        int offset = (page - 1) * 20;  // 计算偏移量

        List<ShoppingInfoVO> lsg = shoppingInfoMapper.list(offset,userId);
        return lsg;
    }

    @Override
    public void update(Long userId, Long shoppingId) {
        shoppingInfoMapper.updateState(userId);
        shoppingInfoMapper.updateSState(shoppingId);

    }
}
