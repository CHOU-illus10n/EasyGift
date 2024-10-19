package com.ncepu.easygift.vo;

import lombok.Data;
import org.apache.ibatis.annotations.Update;

import java.time.LocalDateTime;

/**
 * @author zwy
 * @version 1.0
 * @description: TODO
 * @date 2023/12/30 1:26
 */
@Data
public class ShoppingInfoVO {
    private Long shoppingId;
    private Long userId;
    private String receiverName;
    private String receiverPhone;
    private String receiverProvince;
    private String receiverCity;
    private String receiverDistrict;
    private String receiverAddress;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private Integer state;

}
