package com.ncepu.eg.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.ncepu.eg.mapper.GoodMapper;
import com.ncepu.eg.pojo.GoodInfo;
import com.ncepu.eg.pojo.PageBean;
import com.ncepu.eg.service.GoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author zwy
 * @version 1.0
 * @description: TODO
 * @date 2023/12/14 20:57
 */
@Service
public class GoodServiceImpl implements GoodService {

    @Autowired
    private GoodMapper goodMapper;

    @Override
    public PageBean<GoodInfo> list(Integer pageNum, Integer pageSize, String goodName) {
        //1.创建PageBean对象
        PageBean<GoodInfo> pbi = new PageBean<>();

        //2.开启分页查询 PageHelper
        PageHelper.startPage(pageNum,pageSize);

        List<GoodInfo> as = goodMapper.list(goodName);
        //Page中提供了方法,可以获取PageHelper分页查询后 得到的总记录条数和当前页数据
        Page<GoodInfo> p = (Page<GoodInfo>) as;

        //把数据填充到PageBean对象中
        pbi.setTotal(p.getTotal());
        pbi.setItems(p.getResult());
        return pbi;
    }

    @Override
    public List<GoodInfo> getGoodInfo(Integer id) {
        return goodMapper.getGoodInfo(id);
    }

    @Override
    public void add(GoodInfo goodInfo) {
        goodInfo.setCreateTime(LocalDateTime.now());
        goodInfo.setUpdateTime(LocalDateTime.now());
        goodMapper.add(goodInfo);
    }

    @Override
    public void deleteById(Integer id) {
        goodMapper.deleteById(id);
    }

    @Override
    public GoodInfo getOne(Integer id) {
        return goodMapper.getOne(id);
    }

    @Override
    public void update(GoodInfo goodInfo) {
        goodInfo.setUpdateTime(LocalDateTime.now());
        goodMapper.update(goodInfo);
    }
}
