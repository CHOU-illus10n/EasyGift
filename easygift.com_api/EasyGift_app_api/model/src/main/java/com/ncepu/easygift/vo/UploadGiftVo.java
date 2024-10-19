package com.ncepu.easygift.vo;
/*
@Coding: utf-8
@Author: yizhigopher
@Time: 2023/1/6 23:04
@Software: IntelliJ IDEA
*/

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class UploadGiftVo {
    private String giftName;
    private String description;
    private Long giftTypeId;
    private List<String> giftImgUrls;
    private Double giftPrice;
    private String purchaseTime;
    private String dealTime;
    private Integer giftQuality;
    private String dealAddress;
}
