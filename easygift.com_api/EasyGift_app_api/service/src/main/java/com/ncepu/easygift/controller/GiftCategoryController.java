package com.ncepu.easygift.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ncepu.easygift.pojo.GiftCategory;
import com.ncepu.easygift.result.R;
import com.ncepu.easygift.service.GiftCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/easygift")
@CrossOrigin
public class GiftCategoryController {

    @Autowired
    private GiftCategoryService giftCategoryService;

    @GetMapping("/category")
    public R getAll(){
        List<GiftCategory> res = giftCategoryService.getCategortList();
        return R.ok().data("list",res);
    }
}
