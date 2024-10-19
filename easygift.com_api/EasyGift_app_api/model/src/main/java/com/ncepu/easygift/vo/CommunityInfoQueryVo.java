package com.ncepu.easygift.vo;

import lombok.Data;


@Data
public class CommunityInfoQueryVo {
    private String communityName; // 小区名字
    private String createTimeBegin; // 注册到平台的范围的开始
    private String createTimeEnd; // 注册到平台的范围的截止
}
