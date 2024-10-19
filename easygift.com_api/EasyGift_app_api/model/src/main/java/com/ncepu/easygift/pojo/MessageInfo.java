package com.ncepu.easygift.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;

import lombok.Data;

/**
 *
 * @TableName message_info
 */
@TableName(value ="message_info")
@Data
public class MessageInfo extends BaseEntity implements Serializable {
    /**
     * 留言id
     */
    @TableId(type = IdType.AUTO)
    private Long messageId;

    /**
     * 商品id
     */
    private Long giftId;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 留言内容
     */
    private String content;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
