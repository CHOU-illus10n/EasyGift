package com.ncepu.eg.mapper;

import com.ncepu.eg.pojo.CommunityInfo;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author zwy
 * @version 1.0
 * @description: TODO
 * @date 2023/12/30 15:02
 */
@Mapper
public interface CommunityInfoMapper {
    List<CommunityInfo> list(String communityName);

    @Insert("insert into community_info(community_name, community_address, create_time, update_time) VALUES (#{communityName}, #{communityAddress}, now(), now())")
    void add(CommunityInfo communityInfo);

    @Update("update community_info set community_name=#{communityName}, community_address=#{communityAddress}, update_time=now() where community_id=#{communityId}")
    void update(CommunityInfo communityInfo);

    @Select("select * from community_info where community_id=#{id} and is_deleted=0")
    CommunityInfo getOne(Integer id);

    @Update("update community_info set is_deleted=1 where community_id=#{id}")
    void deleteById(Integer id);
}
