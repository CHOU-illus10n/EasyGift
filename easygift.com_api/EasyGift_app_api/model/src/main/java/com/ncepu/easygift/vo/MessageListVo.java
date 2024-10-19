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
public class MessageListVo {
    private Long userId;

    /**
     * 小区id
     */
    private Long communityId;

    /**
     * 微信登陆返回的openID
     */
    private String openId;
    /**
     * 用户手机号
     */
    private String phone;
    /**
     * 微信登录返回的用户昵称
     */
    private String nickName;

    /**
     * 头像地址
     */
    private String profileUrl;

    /**
     * 积分大小
     */
    private Integer points;
    private Long messageId;

    /**
     * 商品id
     */
    private Long giftId;

    /**
     * 用户id
     */
    /**
     * 留言内容
     */
    private String content;

    private Date createTime;

}
