package com.ncepu.eg.pojo;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class GoodOrder {

  private Integer orderId;
  private Integer state;
  private Integer receiverId;
  private Integer goodId;
  private Integer shoppingId;
  private double payment;
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime sendTime;
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime endTime;
  private LocalDateTime createTime;
  private LocalDateTime updateTime;
  private Integer isDeleted;




}
