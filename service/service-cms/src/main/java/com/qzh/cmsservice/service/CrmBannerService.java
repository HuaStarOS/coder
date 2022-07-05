package com.qzh.cmsservice.service;

import com.qzh.cmsservice.entity.CrmBanner;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 首页banner表 服务类
 * </p>
 *
 * @author qzh
 * @since 2021-04-24
 */
public interface CrmBannerService extends IService<CrmBanner> {

    /**
     * 查询所有的Banner
     * @return
     */
    List<CrmBanner> getAllBanner();
}
