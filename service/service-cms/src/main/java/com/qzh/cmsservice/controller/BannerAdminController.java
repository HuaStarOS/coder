package com.qzh.cmsservice.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qzh.cmsservice.common.Result;
import com.qzh.cmsservice.entity.CrmBanner;
import com.qzh.cmsservice.service.CrmBannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * <p>
 * 后台banner 控制器
 * </p>
 *
 * @author qzh
 * @since 2021-04-24
 */
@RestController
@RequestMapping("/cmsservice/banneradmin")
//@CrossOrigin
public class BannerAdminController {

    @Autowired
    private CrmBannerService crmBannerService;

    /**
     * 分页查询 Banner
     * @param page
     * @param limit
     * @return
     */
    @GetMapping("pageBanner/{page}/{limit}")
    public Result pageBanner(@PathVariable("page") Long page,@PathVariable("limit") Long limit){
        Page<CrmBanner> pageBanner = new Page<>(page, limit);
        QueryWrapper<CrmBanner> wrapper = new QueryWrapper<>();
        wrapper.orderByAsc("sort");
        crmBannerService.page(pageBanner, wrapper);
        return Result.ok().data("items", pageBanner.getRecords()).data("total", pageBanner.getTotal());
    }

    //添加banner
    @PostMapping("/addBanner")
    public Result addBanner(@RequestBody CrmBanner crmBanner){
        crmBanner.setGmtCreate(new Date());
        crmBanner.setGmtModified(new Date());
        boolean flag = crmBannerService.save(crmBanner);
        if (flag){
            return Result.ok();
        }else {
            return Result.error();
        }
    }

    //修改banner
    @PostMapping("/updateBanner")
    public Result updateBanner(@RequestBody CrmBanner crmBanner){
        boolean flag = crmBannerService.updateById(crmBanner);
        if (flag){
            return Result.ok();
        }else {
            return Result.error();
        }
    }

    //根据id删除banner
    @DeleteMapping("/deleteBannerById/{id}")
    public Result deleteBannerById(@PathVariable String id){
        boolean flag = crmBannerService.removeById(id);
        if (flag){
            return Result.ok();
        }else {
            return Result.error();
        }
    }

    //根据id查询banner
    @GetMapping("/getBannerById/{id}")
    public Result getBannerById(@PathVariable String id){
        CrmBanner crmBanner = crmBannerService.getById(id);
        return Result.ok().data("item",crmBanner);
    }


}