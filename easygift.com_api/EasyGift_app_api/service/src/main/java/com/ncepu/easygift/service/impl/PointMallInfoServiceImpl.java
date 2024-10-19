package com.ncepu.easygift.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ncepu.easygift.pojo.PointMallInfo;
import com.ncepu.easygift.service.PointMallInfoService;
import com.ncepu.easygift.mapper.PointMallInfoMapper;
import com.ncepu.easygift.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class PointMallInfoServiceImpl extends ServiceImpl<PointMallInfoMapper, PointMallInfo>
    implements PointMallInfoService{

    @Autowired
    UserInfoService userInfoService;

    @Override
    public Page<PointMallInfo> getPointMallPage(Integer pageNum, Integer pageSize) {
        // 创建分页对象
        Page<PointMallInfo> page = new Page<>(pageNum, pageSize);
        // 调用分页方法
        Page<PointMallInfo> pointMallInfoPage = baseMapper.selectPage(page, null);
        return pointMallInfoPage;
    }

    @Override
    public void saveItem(PointMallInfo pointMallInfo) {
        baseMapper.insert(pointMallInfo);
    }

    @Override
    public List<PointMallInfo> appGetPointMall() {
        List<PointMallInfo> pointMallInfoList = baseMapper.selectList(null);
        return pointMallInfoList;
    }
}




