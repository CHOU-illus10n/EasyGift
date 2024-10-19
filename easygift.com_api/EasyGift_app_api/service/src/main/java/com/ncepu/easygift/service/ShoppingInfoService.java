package com.ncepu.easygift.service;

import com.ncepu.easygift.pojo.ShoppingInfo;
import com.ncepu.easygift.vo.ShoppingInfoVO;

import java.util.List;

/**
 * @author zwy
 * @version 1.0
 * @description: TODO
 * @date 2023/12/29 20:47
 */
public interface ShoppingInfoService {
    void addAddress(String receiverName, String phone, Long userId, String address,String receiverAddress,int state);

    List<ShoppingInfoVO> list(int page, int userId);

    void update(Long userId, Long shoppingId);
}
