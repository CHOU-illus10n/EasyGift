package com.ncepu.easygift.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ncepu.easygift.pojo.GoodInfo;
import com.ncepu.easygift.pojo.PageBean;
import com.ncepu.easygift.vo.PublishVo;

import java.util.List;

public interface GoodService {

    List<GoodInfo> list(Integer page);

    List<GoodInfo> getGoodListByCategory(Long categoryId, int page);

    List<GoodInfo> getDetailInfo(Long goodId);

    void buyGood(Long goodId, Long userId,Integer goodCount);
}
