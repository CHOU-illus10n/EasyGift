package com.ncepu.easygift.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ncepu.easygift.pojo.GiftCategory;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;


public interface GiftCategoryService extends IService<GiftCategory> {

    Page<GiftCategory> getGoodTypePage(Integer pageNum, Integer pageSize);
    List<GiftCategory> getCategortList();
}
