package com.ncepu.easygift.mapper;

import com.github.yulichang.base.MPJBaseMapper;
import com.ncepu.easygift.pojo.GoodOrder;
import com.ncepu.easygift.pojo.ShoppingInfo;
import com.ncepu.easygift.vo.ShoppingInfoVO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * @author zwy
 * @version 1.0
 * @description: TODO
 * @date 2023/12/29 21:02
 */
@Mapper
public interface ShoppingInfoMapper extends MPJBaseMapper<ShoppingInfoVO> {
    @Insert("insert into shopping_info(receiver_name,receiver_phone,user_id,receiver_province,receiver_city,receiver_district,receiver_address,state,create_time,update_time) values(#{receiverName},#{phone},#{userId},#{receiverProvince},#{receiverCity},#{receiverDistrict},#{receiverAddress},#{state},now(),now())")
    void addAddress(String receiverName, String phone, Long userId, String receiverProvince, String receiverCity, String receiverDistrict, String receiverAddress, int state);

    @Update("update shopping_info set state = 0 where user_id = #{userId}")
    void updateState(Long userId);

    @Select("select * from shopping_info where user_id = #{userId} order by state desc limit #{offset},20 ")
    List<ShoppingInfoVO> list(int offset, int userId);

    @Update("update shopping_info set state = 1 where shopping_id = #{shoppingId} ")
    void updateSState(Long shoppingId);

    @Select("select count(*) from shopping_info where user_id = #{userId} and state = 1")
    int count(Long userId);
}
