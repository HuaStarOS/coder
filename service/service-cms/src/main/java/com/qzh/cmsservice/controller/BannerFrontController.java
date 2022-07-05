package com.qzh.cmsservice.controller;

import com.qzh.cmsservice.common.Result;
import com.qzh.cmsservice.entity.CrmBanner;
import com.qzh.cmsservice.service.CrmBannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author: qzh
 * @Date: 2021/4/27 13:26
 */
@RestController
@RequestMapping("/cmsservice/bannerfront")
//@CrossOrigin
public class BannerFrontController {

    @Autowired
    private CrmBannerService crmBannerService;

    //查询所有幻灯片
    @GetMapping("/getAllBanner")
    public Result getAll(){
        List<CrmBanner> list = crmBannerService.getAllBanner();
        return Result.ok().data("list",list);
    }

}
