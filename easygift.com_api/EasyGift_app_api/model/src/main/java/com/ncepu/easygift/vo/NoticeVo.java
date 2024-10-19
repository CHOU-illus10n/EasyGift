package com.ncepu.easygift.vo;

import lombok.Data;

import java.sql.Date;

/**
 * @Author：zyf
 * @Package：com.ncepu.easygift.vo
 * @Project：easygift_wxapp_back
 * @Date：2023/12/19 17:04
 * @Filename：PublishVo
 */
@Data
public class NoticeVo {

    private Long giftId;
    private String giftName;
    private Date createTime;
    private Long receiverId;
    private String receiverName;
    private Long senderId;
    private String senderName;
    private Integer giftOrderStatus;
    private String detail;

}
