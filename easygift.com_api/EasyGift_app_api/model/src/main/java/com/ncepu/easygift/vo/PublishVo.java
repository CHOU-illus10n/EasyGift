package com.ncepu.easygift.vo;

import lombok.Data;

import java.sql.Date;

/**
 * @Author：zyf
 * @Package：com.ncepu.easygift.vo
 * @Project：easygift_wxapp_back
 * @name：PublishVo
 * @Date：2023/12/19 17:04
 * @Filename：PublishVo
 */
@Data
public class PublishVo {
    private Long userId;
    private Long giftId;
    private String openId;
    private String giftImgUrl;
    private String giftName;
    private Long categoryId;
    private Long communityId;
    private Integer giftQuality;//
    private Double giftOriginPrice;//
    private Date purchaseTime;//
    private Integer state;
    private String profileUrl;
    private String nickName;
    private String communityName;
    private String categoryName;
    private Date createTime;
    private String dealAddress;
    private String description;


}
