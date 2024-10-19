package com.ncepu.easygift.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;

import lombok.Data;

/**
 *
 * @TableName sys_good_type_info
 */
@TableName(value ="gift_category")
@Data
public class GiftCategory extends BaseEntity implements Serializable {
    /**
     * 商品类型id
     */
    @TableId(type = IdType.AUTO)
    private Long categoryId;

    /**
     * 商品类型名称
     */
    private String categoryName;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
