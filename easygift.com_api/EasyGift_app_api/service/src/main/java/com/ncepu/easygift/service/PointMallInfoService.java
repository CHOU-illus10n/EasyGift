package com.ncepu.easygift.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ncepu.easygift.pojo.PointMallInfo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;


public interface PointMallInfoService extends IService<PointMallInfo> {
    Page<PointMallInfo> getPointMallPage(Integer pageNum, Integer pageSize);

    void saveItem(PointMallInfo pointMallInfo);
    List<PointMallInfo> appGetPointMall();
}
