package com.ncepu.easygift.controller;

import com.ncepu.easygift.result.R;
import com.ncepu.easygift.service.ObsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;


@RestController
@RequestMapping("/easygift/obs")
@CrossOrigin
public class ObsController {
//CHANGE！
    @Autowired
    private ObsService obsService;

    // 文件上传 -- file格式
    @PostMapping("/upload")
    public R upload(MultipartFile file) {
        String imgUrl = obsService.upload(file);
        return R.ok().data("url", imgUrl);
    }

}
