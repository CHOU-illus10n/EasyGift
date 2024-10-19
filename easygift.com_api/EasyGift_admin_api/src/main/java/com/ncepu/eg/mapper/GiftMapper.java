package com.ncepu.eg.mapper;

import com.ncepu.eg.pojo.GiftInfo;
import com.ncepu.eg.pojo.GiftVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface GiftMapper {



    List<GiftVO> list(Integer categoryId, Integer state);

    @Select("select * from gift_info where is_deleted = 0")
    List<GiftInfo> listDetail();

    @Select("select * from gift_info")
    List<GiftVO> listAll();

    @Select("select * from gift_info where gift_id=#{id}")
    GiftVO getOne(Integer id);

    @Update("update gift_info set state=#{state} where gift_id=#{id}")
    void changeState(Integer id,Integer state);

    @Update("update gift_info set is_deleted = 1 where gift_id = #{id}")
    void delete(Integer id);
}
