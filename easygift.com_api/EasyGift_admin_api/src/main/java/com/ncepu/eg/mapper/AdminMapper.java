package com.ncepu.eg.mapper;


import com.ncepu.eg.pojo.Admin;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface AdminMapper {
    @Select("select * from admin where user_name=#{adminUserAccount}")
    Admin findByAdminAccount(String adminUserAccount);

    @Update("update admin set user_pic=#{userPic},update_time=now() where user_name=#{userName}")
    void updateAvatar(Admin admin);

    @Update("update admin set password = #{password},update_time=now() where user_id=#{userId}")
    void update(Admin admin);
}
