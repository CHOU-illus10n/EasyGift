package com.ncepu.easygift.mapper;

import com.github.yulichang.base.MPJBaseMapper;
import com.ncepu.easygift.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;


@Mapper
public interface UserMapper extends MPJBaseMapper<User> {

    @Update("update user set points=points-#{points} where user_id=#{userId}")
    void buy(Long userId, Integer goodCount, Integer points);


    void updateInfo(User user);
}




