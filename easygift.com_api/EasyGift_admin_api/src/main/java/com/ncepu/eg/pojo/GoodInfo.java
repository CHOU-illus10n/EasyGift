package com.ncepu.eg.pojo;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class GoodInfo {

  private Integer goodId;
  private Integer goodCategoryId;
  private String goodImg;
  private String goodName;
  private Integer goodPoint;
  private String goodDesc;
  private Integer goodCount;
  private LocalDateTime createTime;
  private LocalDateTime updateTime;
  private Integer isDeleted;



}
