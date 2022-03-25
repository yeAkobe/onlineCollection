package com.zzx.eduservice.controller;

import com.zzx.commonutils.R;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/eduservice/user")
//@CrossOrigin
//解决跨域问题
public class EduLoginController {
    @PostMapping("/login")
    public
    R Login(){
        return R.ok().data("token","admin");
    }
    @GetMapping("/info")
    public R info(){
        return R.ok().data("roles","admin").data("name","阿叶").data("avatar","https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
    }
}
