package com.ncepu.easygift.vo;
/*
@Coding: utf-8
@Author: yizhigopher
@Time: 2022/10/19 19:12
@Software: IntelliJ IDEA
*/

import lombok.Data;

@Data
public class UserRegisterVo {
    private String openId;
    private String nickName;
    private String phone;
    private Long communityId;
    private String faceUrl;
}
