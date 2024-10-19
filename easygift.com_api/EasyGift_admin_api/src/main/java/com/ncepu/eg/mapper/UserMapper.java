package com.ncepu.eg.mapper;

import com.ncepu.eg.pojo.Admin;
import com.ncepu.eg.pojo.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserMapper {
    //根据用户名查询用户
    @Select("select * from admin where user_name=#{username}")
    Admin findByUserName(String username);

    //添加
    @Insert("insert into user(username,password,create_time,update_time)" +
            " values(#{username},#{password},now(),now())")
    void add(String username, String password);

    @Update("update user set nickname=#{nickname},email=#{email},update_time=#{updateTime} where id=#{id}")
    void update(User user);

    @Update("update user set user_pic=#{avatarUrl},update_time=now() where id=#{id}")
    void updateAvatar(String avatarUrl,Integer id);

    @Update("update user set password=#{md5String},update_time=now() where id=#{id}")
    void updatePwd(String md5String, Integer id);


    List<User> list(String nickName);

    @Select("select * from eg_db.user where nick_name=#{nickName}")
    User getOne(String nickName);

    @Delete("delete from user where user_id =#{id}")
    void delete(Integer id);
}
