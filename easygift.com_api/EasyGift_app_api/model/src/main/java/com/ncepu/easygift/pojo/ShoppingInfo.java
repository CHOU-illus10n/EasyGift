package com.ncepu.easygift.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ShoppingInfo {
  @TableId(type = IdType.AUTO)
  private Long shoppingId;
  private Long userId;
  private String receiverName;
  private String receiverPhone;
  private String receiverProvince;
  private String receiverCity;
  private String receiverDistrict;
  private String receiverAddress;
  private String address;
  private LocalDateTime createTime;
  private LocalDateTime updateTime;
  private Integer state;




}
