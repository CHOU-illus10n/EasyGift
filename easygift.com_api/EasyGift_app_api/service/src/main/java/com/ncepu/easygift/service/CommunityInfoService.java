package com.ncepu.easygift.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ncepu.easygift.pojo.CommunityInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ncepu.easygift.vo.CommunityInfoQueryVo;

import java.util.List;
import java.util.Map;


public interface CommunityInfoService extends IService<CommunityInfo> {

    Page<CommunityInfo> getCommunityInfoPage(Integer pageNum, Integer pageSize, CommunityInfoQueryVo communityInfoQueryVo);

    List<Map<String, Object>> getAll();

}
