package com.ncepu.easygift.vo;

import lombok.Data;

import java.util.Date;


@Data
public class GiftListVo {
    private Long giftId;
    private String giftImgUrl;
    private Long communityId;
    private Long userId;
    private String giftName;
    private String description;
    private Long categoryId;
    private Double giftOriginPrice;
    private Date purchaseTime;
    private Date dealTime;
    private Integer giftQuality;
    private String dealAddress;
    private Integer failureTypeId;
    private Integer state;
}
