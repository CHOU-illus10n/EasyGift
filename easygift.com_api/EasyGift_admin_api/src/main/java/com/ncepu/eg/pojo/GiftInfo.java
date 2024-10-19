package com.ncepu.eg.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class GiftInfo {

  private Integer giftId;
  private String userId;
  private String giftName;
  private String description;
  private String categoryId;
  private double giftOriginPrice;
  private LocalDate purchaseTime;
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime dealTime;
  private String dealAddress;
  private long giftQuality;
  private Integer state;
  private String failureTypeId;
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime createTime;
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime updateTime;
  private long isDeleted;



}
