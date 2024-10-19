package com.ncepu.easygift.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;


@Data
public class BaseEntity {
    /**
     * 创建时间
     */
    protected Date createTime;

    /**
     * 更新时间
     */
    protected Date updateTime;

    /**
     * 是否被删除
     */
    @TableLogic
    protected Integer isDeleted;

    @TableField(exist = false)
    protected Map<String, Object> paramString = new HashMap<>();
}
