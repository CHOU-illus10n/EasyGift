package com.ncepu.eg.service;

import com.ncepu.eg.pojo.GiftOrder;
import com.ncepu.eg.pojo.PageBean;

public interface GiftOrderService {
    PageBean<GiftOrder> list(Integer pageNum, Integer pageSize);

    void delete(Integer orderId);
}
