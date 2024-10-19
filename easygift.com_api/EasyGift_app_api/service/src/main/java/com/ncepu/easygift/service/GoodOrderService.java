package com.ncepu.easygift.service;

import com.ncepu.easygift.pojo.GoodInfo;
import com.ncepu.easygift.pojo.GoodOrder;
import com.ncepu.easygift.vo.GoodOrderVO;

import java.util.List;

/**
 * @author zwy
 * @version 1.0
 * @description: TODO
 * @date 2023/12/28 22:47
 */
public interface GoodOrderService {
    List<GoodOrderVO> list(int page, int userId);

    void change(Long orderId, int userId, Integer state);
}
