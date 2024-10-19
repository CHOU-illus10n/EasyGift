package com.ncepu.eg.mapper;

import com.ncepu.eg.pojo.GiftOrder;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface GiftOrderMapper {

    @Select("select * from gift_order where is_deleted = 0 and gift_order_status =4")
    List<GiftOrder> list();

    @Update("update gift_order set is_deleted = 1 where gift_order_id = #{orderId}")
    void delete(Integer orderId);
}
