package com.ncepu.easygift.controller;

import com.ncepu.easygift.result.R;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/eastgift")
@CrossOrigin
public class TestController {

    @GetMapping("/docker")
    public R dockerTest() {
        return R.ok().message("测试部署新jar包成功");
    }
}
