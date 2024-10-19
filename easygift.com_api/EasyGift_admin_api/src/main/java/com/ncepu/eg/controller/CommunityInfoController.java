package com.ncepu.eg.controller;

import com.ncepu.eg.pojo.CommunityInfo;
import com.ncepu.eg.pojo.GiftOrder;
import com.ncepu.eg.pojo.PageBean;
import com.ncepu.eg.pojo.Result;
import com.ncepu.eg.service.CommunityInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author zwy
 * @version 1.0
 * @description: TODO
 * @date 2023/12/30 14:59
 */
@RestController
@RequestMapping("/communityInfo")
public class CommunityInfoController {

    @Autowired
    private CommunityInfoService communityInfoService;
    @GetMapping
    public Result<PageBean<CommunityInfo>> list(Integer pageNum, Integer pageSize,
                                                @RequestParam(required = false) String communityName) {
        PageBean<CommunityInfo> pbg=  communityInfoService.list( pageNum,  pageSize,communityName);
        return Result.success(pbg);
    }

    @PostMapping("/add")
    public Result add(@RequestBody CommunityInfo communityInfo) {
        communityInfoService.add(communityInfo);
        return Result.success();
    }

    @PostMapping("/update")
    public Result update(@RequestBody CommunityInfo communityInfo) {
        communityInfoService.update(communityInfo);
        return Result.success();
    }

    @GetMapping("/getOne")
    public Result<CommunityInfo> getOne(Integer id) {
        CommunityInfo communityInfo = communityInfoService.getOne(id);
        return Result.success(communityInfo);
    }

    @PostMapping("/delete")
    public Result delete(Integer id) {
        communityInfoService.deleteById(id);
        return Result.success();
    }
}
