package com.ncepu.eg.mapper;

import com.ncepu.eg.pojo.GiftImgVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface GiftImgMapper {
    @Select("select gift_img_id,gift_id,user_id,gift_img_url,is_deleted from gift_img where gift_id = #{giftId}")
    List<GiftImgVO> list(Integer giftId);
}
