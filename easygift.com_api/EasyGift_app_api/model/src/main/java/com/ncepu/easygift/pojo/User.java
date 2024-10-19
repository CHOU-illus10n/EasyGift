package com.ncepu.easygift.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;

import lombok.Data;

/**
 *
 * @TableName user_info
 */
@TableName(value ="user")
@Data
public class User extends BaseEntity implements Serializable {
    /**
     * 用户id
     */
    @TableId(type = IdType.AUTO)
    private Long userId;

    /**
     * 小区id
     */
    private Long communityId;

    /**
     * 微信登陆返回的openID
     */
    private String openId;
    /**
     * 用户手机号
     */
    private String phone;
    /**
     * 微信登录返回的用户昵称
     */
    private String nickName;

    /**
     * 头像地址
     */
    private String profileUrl;

    /**
     * 积分大小
     */
    private Integer points;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
