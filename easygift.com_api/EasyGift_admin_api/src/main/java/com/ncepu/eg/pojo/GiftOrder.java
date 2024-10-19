package com.ncepu.eg.pojo;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class GiftOrder {

  private Long giftOrderId;
  private Integer giftOrderStatus;
  private Long giftId;
  private Long senderId;
  private Long receiverId;
  private Integer incrPoint;
  private LocalDateTime createTime;
  private LocalDateTime updateTime;
  private Integer isDeleted;



}
