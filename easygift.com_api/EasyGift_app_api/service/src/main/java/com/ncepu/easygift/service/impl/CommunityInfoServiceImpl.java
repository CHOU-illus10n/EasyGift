package com.ncepu.easygift.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ncepu.easygift.pojo.CommunityInfo;
import com.ncepu.easygift.service.CommunityInfoService;
import com.ncepu.easygift.mapper.CommunityInfoMapper;
import com.ncepu.easygift.vo.CommunityInfoQueryVo;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class CommunityInfoServiceImpl extends ServiceImpl<CommunityInfoMapper, CommunityInfo>
    implements CommunityInfoService{

    @Override
    public Page<CommunityInfo> getCommunityInfoPage(Integer pageNum, Integer pageSize, CommunityInfoQueryVo communityInfoQueryVo) {
        QueryWrapper<CommunityInfo> queryWrapper = new QueryWrapper<>();
        if(!StringUtils.isEmpty(communityInfoQueryVo.getCommunityName())) {
            queryWrapper.like("community_name", communityInfoQueryVo.getCommunityName());
        }
        if(!StringUtils.isEmpty(communityInfoQueryVo.getCreateTimeBegin())) {
            queryWrapper.ge("create_time", communityInfoQueryVo.getCreateTimeBegin());
        }
        if(!StringUtils.isEmpty(communityInfoQueryVo.getCreateTimeBegin())) {
            queryWrapper.le("create_time", communityInfoQueryVo.getCreateTimeEnd());
        }
        Page<CommunityInfo> communityInfoPage = new Page<>(pageNum, pageSize);
        baseMapper.selectPage(communityInfoPage, queryWrapper);
        return communityInfoPage;
    }

    @Override
    public List<Map<String, Object>> getAll() {
        // 获取所有的小区信息【id，name即可】
        List<CommunityInfo> communityInfoList = baseMapper.selectList(null);
        List<Map<String, Object>> list = new ArrayList<>();

        for (CommunityInfo communityInfo : communityInfoList) {
            Map<String, Object> res = new HashMap<>();
            res.put("communityId", communityInfo.getCommunityId());
            res.put("communityName", communityInfo.getCommunityName());
            list.add(res);
        }
        return list;
    }
}




