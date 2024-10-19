package com.ncepu.eg.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @author zwy
 * @version 1.0
 * @description: TODO
 * @date 2023/12/13 10:13
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GiftVO {
    private String giftId;
    private String userId;
    private String giftName;
    private String description;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime dealTime;
    private Integer state;

}
