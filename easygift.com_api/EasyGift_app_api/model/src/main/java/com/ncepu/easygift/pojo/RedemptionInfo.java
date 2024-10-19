package com.ncepu.easygift.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;

import lombok.Data;

/**
 *
 * @TableName redemption_info
 */
@TableName(value ="redemption_info")
@Data
public class RedemptionInfo extends BaseEntity implements Serializable {
    /**
     * 积分兑换记录主键id
     */
    @TableId
    private Long recordId;

    /**
     * 兑换的物品id
     */
    private Long goodId;

    /**
     * 兑换的用户id
     */
    private Long userId;

    /**
     * 单次兑换扣除积分
     */
    private Integer decrPoint;

    private Integer goodCount;

    private Integer isDeleted;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
