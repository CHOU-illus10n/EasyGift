package com.ncepu.eg.service;

import com.ncepu.eg.pojo.GoodInfo;
import com.ncepu.eg.pojo.PageBean;

import java.util.List;

public interface GoodService {
    PageBean<GoodInfo> list(Integer pageNum, Integer pageSize, String goodName);

    List<GoodInfo> getGoodInfo(Integer id);

    void add(GoodInfo goodInfo);

    void deleteById(Integer id);

    GoodInfo getOne(Integer id);

    void update(GoodInfo goodInfo);
}
