package com.ncepu.easygift.vo;

import lombok.Data;

import java.util.Date;


@Data
public class ISenderVo {
    private String giftName;
    private Long orderId;

    /**
     * 订单状态：3->进行中，4->赠送完成，-1->赠送失败
     */
    private Integer giftOrderStatus;

    /**
     * 商品id
     */
    private Long giftId;

    /**
     * 赠送方用户id
     */

    /**
     * 接收方用户id
     */

    /**
     * 单条订单添加的积分
     */
    private Integer incrPoint;

    private String phone;
    private Date dealTime;
    /**
     * 微信登录返回的用户昵称
     */

    /**
     * 头像地址
     */
    private String profileUrl;
    private String giftImgUrl;
    private String receiverName;
    private String senderName;
}
