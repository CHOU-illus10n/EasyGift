package com.ncepu.easygift.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;

import lombok.Data;

/**
 *
 * @TableName point_mall_info
 */
@TableName(value ="point_mall_info")
@Data
public class PointMallInfo extends BaseEntity implements Serializable {
    /**
     * 积分商城物品
     */
    @TableId
    private String itemId;

    /**
     * 积分物品名称
     */
    private String itemName;

    /**
     * 积分物品兑换所需积分
     */
    private Integer itemPoint;

    /**
     * 积分物品描述
     */
    private String itemDesc;

    /**
     * 积分物品数量
     */
    private Integer itemCount;

    /**
     * 积分物品照片地址
     */
    private String itemPicUrl;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
