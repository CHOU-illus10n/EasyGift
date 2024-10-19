package com.ncepu.eg.service;

import com.ncepu.eg.pojo.GoodOrder;
import com.ncepu.eg.pojo.PageBean;

public interface GoodOrderService {
    PageBean<GoodOrder> list(Integer pageNum, Integer pageSize, Integer orderId);

    void send(GoodOrder goodOrder);
}
