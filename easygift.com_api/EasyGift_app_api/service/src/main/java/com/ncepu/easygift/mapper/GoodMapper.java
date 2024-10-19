package com.ncepu.easygift.mapper;

import com.github.yulichang.base.MPJBaseMapper;
import com.ncepu.easygift.pojo.GiftInfo;
import com.ncepu.easygift.pojo.GoodInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * @author zwy
 * @version 1.0
 * @description: TODO
 * @date 2023/12/27 19:40
 */
@Mapper
public interface GoodMapper extends MPJBaseMapper<GoodInfo> {

    @Update("update good_info set good_count=good_count-#{goodCount} where good_id=#{goodId}")
    void buy(Long goodId, Integer goodCount);

    @Select("select good_point from good_info where good_id=#{goodId}")
    Integer getPoint(Long goodId);
}
