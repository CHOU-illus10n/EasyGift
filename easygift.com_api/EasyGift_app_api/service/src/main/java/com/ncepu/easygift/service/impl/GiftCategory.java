package com.ncepu.easygift.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ncepu.easygift.service.GiftCategoryService;
import com.ncepu.easygift.mapper.GiftCategoryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @description 针对表【gift_category】的数据库操作Service实现
*/
@Service
public class GiftCategory extends ServiceImpl<GiftCategoryMapper, com.ncepu.easygift.pojo.GiftCategory>
    implements GiftCategoryService {

    @Autowired
    GiftCategoryMapper giftCategoryMapper;

    @Override
    public Page<com.ncepu.easygift.pojo.GiftCategory> getGoodTypePage(Integer pageNum, Integer pageSize) {
        Page<com.ncepu.easygift.pojo.GiftCategory> page = new Page<>(pageNum, pageSize);
        baseMapper.selectPage(page, null);
        return page;
    }

    @Override
    public List<com.ncepu.easygift.pojo.GiftCategory> getCategortList() {
        return giftCategoryMapper.selectList(null);
    }
}




