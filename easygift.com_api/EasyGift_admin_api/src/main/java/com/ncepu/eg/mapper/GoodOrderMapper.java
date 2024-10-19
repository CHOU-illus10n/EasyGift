package com.ncepu.eg.mapper;

import com.ncepu.eg.pojo.GoodOrder;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface GoodOrderMapper {


    List<GoodOrder> list(Integer orderId);
    @Update("update good_order set state=#{state} ,send_time = now() where order_id=#{orderId}")
    void send(GoodOrder goodOrder);

    @Update("update good_order set state=#{state},end_time=now() where order_id=#{orderId}")
    void cancel(GoodOrder goodOrder);
}
