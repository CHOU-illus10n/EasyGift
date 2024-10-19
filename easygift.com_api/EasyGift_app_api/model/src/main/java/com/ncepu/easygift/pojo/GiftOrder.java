package com.ncepu.easygift.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;

import lombok.Data;

/**
 *
 * @TableName order_info
 */
@TableName(value ="gift_order")
@Data
public class GiftOrder extends BaseEntity implements Serializable {
    /**
     * 订单id
     */
    @TableId(type = IdType.AUTO)
    private Long giftOrderId;

    /**
     * 订单状态：3->进行中，4->赠送完成，-1->赠送失败
     */
    private Integer giftOrderStatus;

    /**
     * 商品id
     */
    private Long giftId;

    /**
     * 赠送方用户id
     */
    private Long senderId;

    /**
     * 接收方用户id
     */
    private Long receiverId;

    /**
     * 单条订单添加的积分
     */
    private Integer incrPoint;


    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
