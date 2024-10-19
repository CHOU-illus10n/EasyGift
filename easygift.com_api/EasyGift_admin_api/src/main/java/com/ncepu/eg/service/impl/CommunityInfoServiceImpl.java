package com.ncepu.eg.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.ncepu.eg.mapper.CommunityInfoMapper;
import com.ncepu.eg.pojo.CommunityInfo;
import com.ncepu.eg.pojo.GiftVO;
import com.ncepu.eg.pojo.PageBean;
import com.ncepu.eg.service.CommunityInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author zwy
 * @version 1.0
 * @description: TODO
 * @date 2023/12/30 15:00
 */
@Service
public class CommunityInfoServiceImpl implements CommunityInfoService {

    @Autowired
    private CommunityInfoMapper communityInfoMapper;
    @Override
    public PageBean<CommunityInfo> list(Integer pageNum, Integer pageSize,String communityName) {

        //1.创建PageBean对象
        PageBean<CommunityInfo> pb = new PageBean<>();

        //2.开启分页查询 PageHelper
        PageHelper.startPage(pageNum,pageSize);

        List<CommunityInfo> as = communityInfoMapper.list(communityName);
        //Page中提供了方法,可以获取PageHelper分页查询后 得到的总记录条数和当前页数据
        Page<CommunityInfo> p = (Page<CommunityInfo>) as;

        //把数据填充到PageBean对象中
        pb.setTotal(p.getTotal());
        pb.setItems(p.getResult());
        return pb;
    }

    @Override
    public void add(CommunityInfo communityInfo) {
        communityInfoMapper.add(communityInfo);
    }

    @Override
    public void update(CommunityInfo communityInfo) {
        communityInfoMapper.update(communityInfo);
    }

    @Override
    public CommunityInfo getOne(Integer id) {
        return communityInfoMapper.getOne(id);
    }

    @Override
    public void deleteById(Integer id) {
        communityInfoMapper.deleteById(id);
    }
}
