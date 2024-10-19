package com.ncepu.easygift.vo;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class IReceiveVO {
  private Long giftId;
  private String description;
  private String giftImgUrl;
  private String giftName;
  private String dealAddress;
  private String dealTime;
  private String phone;
  private int state;

}
