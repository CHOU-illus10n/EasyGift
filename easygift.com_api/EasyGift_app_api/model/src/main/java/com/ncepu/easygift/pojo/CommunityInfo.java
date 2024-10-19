package com.ncepu.easygift.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;

import lombok.Data;

/**
 *
 * @TableName community_info
 */
@TableName(value ="community_info")
@Data
public class CommunityInfo extends BaseEntity implements Serializable {
    /**
     * 小区id
     */
    @TableId(value="community_id",type = IdType.AUTO)
    private long communityId;

    /**
     * 小区名称
     */
    private String communityName;

    /**
     * 小区地址
     */
    private String communityAddress;

    /**
     * 小区照片地址
     */

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
