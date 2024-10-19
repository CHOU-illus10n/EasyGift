package com.ncepu.easygift.mapper;

import com.github.yulichang.base.MPJBaseMapper;
import com.ncepu.easygift.pojo.GoodOrder;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

/**
 * @author zwy
 * @version 1.0
 * @description: TODO
 * @date 2023/12/28 22:48
 */
@Mapper
public interface GoodOrderMapper extends MPJBaseMapper<GoodOrder> {
    @Insert("insert into good_order (receiver_id,good_id,good_count,create_time,update_time,payment,state) values (#{userId},#{goodId},#{goodCount},now(),now(),#{points},3)")
    void insertOrder(Long userId, Long goodId, Integer goodCount,Integer points);

    @Update("update good_order set state=#{state},end_time=now() where order_id=#{orderId} and receiver_id=#{userId}")
    void changeState(Long orderId, int userId, Integer state);


}
