package com.ncepu.easygift.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ncepu.easygift.pojo.CommunityInfo;
import com.ncepu.easygift.result.R;
import com.ncepu.easygift.service.CommunityInfoService;
import com.ncepu.easygift.vo.CommunityInfoQueryVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@CrossOrigin
@RestController
@RequestMapping("/easygift/community")
public class CommunityInfoController {

    @Autowired
    private CommunityInfoService communityInfoService;

    @GetMapping("/all")
    public R appGetList(){
        List<Map<String, Object>> list = communityInfoService.getAll();
        return R.ok().data("list", list);
    }
}
