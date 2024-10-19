package com.ncepu.eg.service;

import com.ncepu.eg.pojo.CommunityInfo;
import com.ncepu.eg.pojo.PageBean;

/**
 * @author zwy
 * @version 1.0
 * @description: TODO
 * @date 2023/12/30 15:00
 */
public interface CommunityInfoService {
    PageBean<CommunityInfo> list(Integer pageNum, Integer pageSize,String communityName);

    void add(CommunityInfo communityInfo);

    void update(CommunityInfo communityInfo);

    CommunityInfo getOne(Integer id);

    void deleteById(Integer id);
}
