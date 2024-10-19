package com.ncepu.eg.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.groups.Default;
import lombok.Data;

import java.time.LocalDateTime;
@Data
public class Category {
    private String categoryId;
    private String categoryName;
    private LocalDateTime  createTime;
    private LocalDateTime  updateTime;
    private Integer isDeleted;
}
