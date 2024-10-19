package com.ncepu.easygift.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

/**
 *
 * @TableName gift_info
 */
@TableName(value ="gift_info")
@Data
public class GiftInfo extends BaseEntity implements Serializable {
    /**
     * 商品id
     */
    @TableId(type = IdType.AUTO)
    private Long giftId;
    /**
     * 所属小区ID
     */
    private Long communityId;

    /**
     * 商品赠出的用户id
     */
    private Long userId;

    /**
     * 商品名称
     */
    private String giftName;

    /**
     * 商品描述
     */
    private String description;

    /**
     * 商品类别id
     */
    private Long categoryId;

    /**
     * 商品的原来价格
     */
    private Double giftOriginPrice;

    /**
     * 商品购买时间
     */
    private Date purchaseTime;

    /**
     * 商品交易时间
     */

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date dealTime;

    /**
     * 几成新：0->刚买，1->九五新，2->九成新，3->八成新，4->以下
     */
    private Integer giftQuality;

    /**
     * 商品交易地址
     */
    private String dealAddress;

    /**
     * 不通过的原因类型id, 默认成功发布的话是-1，如果是不通过才配合不通过对应的type值
     */
    private Integer state;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
