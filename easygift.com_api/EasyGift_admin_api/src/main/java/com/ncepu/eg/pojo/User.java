package com.ncepu.eg.pojo;


import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;
//lombok  在编译阶段,为实体类自动生成setter  getter toString
// pom文件中引入依赖   在实体类上添加注解
@Data
public class User {
    @NotNull
    private Integer userId;
    private String nickName;//用户名
    private String openId;
    private String profileUrl;
    private String communityId;
    private String phone;
    private String points;

    private String isDeleted;

    private LocalDateTime createTime;//创建时间
    private LocalDateTime updateTime;//更新时间
}
