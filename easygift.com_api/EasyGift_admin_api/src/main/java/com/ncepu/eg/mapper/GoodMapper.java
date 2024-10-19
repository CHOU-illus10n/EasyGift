package com.ncepu.eg.mapper;

import com.ncepu.eg.pojo.GoodInfo;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface GoodMapper {

    List<GoodInfo> list(String goodName);


    List<GoodInfo> getGoodInfo(Integer id);

    @Insert("insert into good_info(good_category_id,good_name,good_point,good_desc,good_count,good_img,create_time,update_time,is_deleted) values(#{goodCategoryId},#{goodName},#{goodPoint},#{goodDesc},#{goodCount},#{goodImg},#{createTime},#{updateTime},0) ")
    void add(GoodInfo goodInfo);

    @Delete("delete from good_info where good_id=#{id}")
    void deleteById(Integer id);

    @Select("select * from good_info where good_id=#{id}")
    GoodInfo getOne(Integer id);

    @Update("update good_info set good_category_id=#{goodCategoryId},good_name=#{goodName},good_point=#{goodPoint},good_desc=#{goodDesc},good_count=#{goodCount},good_img=#{goodImg},update_time=#{updateTime} where good_id=#{goodId}")
    void update(GoodInfo goodInfo);
}
