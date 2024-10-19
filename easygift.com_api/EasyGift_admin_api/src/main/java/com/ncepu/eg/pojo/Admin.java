package com.ncepu.eg.pojo;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Admin {

  private String UserId;
  private String userName;
  private String password;
  private String userPic;
  private LocalDateTime createTime;
  private LocalDateTime updateTime;
  private long isDeleted;

}
