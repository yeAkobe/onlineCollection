package com.zzx.cmsservice.controller;

import com.zzx.cmsservice.entity.CrmBanner;
import com.zzx.cmsservice.service.CrmBannerService;
import com.zzx.commonutils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

//@CrossOrigin //解决跨域问题
@RestController
@RequestMapping("/cmsservice/bannerFront")
public class BannerFrontController {

    @Autowired
    private CrmBannerService crmBannerService;

    //查询所有幻灯片
    @GetMapping("getAll")
    public
    R getAll(){
        List<CrmBanner> list = crmBannerService.getAllBanner();
        return R.ok().data("list",list);
    }


}

