package com.ncepu.eg.pojo;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CommunityInfo {

  private Long communityId;
  private String communityName;
  private String communityAddress;
  private String communityPicUrl;
  private LocalDateTime createTime;
  private LocalDateTime updateTime;
  private Integer isDeleted;




}
