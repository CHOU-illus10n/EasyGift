package com.ncepu.easygift.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author：zyf
 * @Package：com.ncepu.easygift.pojo
 * @Project：easygift_wxapp_back
 * @name：GiftImage
 * @Date：2023/12/17 21:22
 * @Filename：GiftImage
 */
@TableName(value = "gift_img")
@Data
public class GiftImage extends BaseEntity implements Serializable {
    /**
     * 物品图片id
     */
    @TableId(type = IdType.AUTO)
    private long giftImgId;

    /**
     * 物品id
     */
    private Long giftId;

    /**
     * 发布用户id
     */
    private Long userId;

    /**
     * 转赠物品图片url
     */
    private String giftImgUrl;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
