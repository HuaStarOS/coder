package com.qzh.cmsservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.qzh.cmsservice.entity.CrmBanner;
import com.qzh.cmsservice.mapper.CrmBannerMapper;
import com.qzh.cmsservice.service.CrmBannerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 首页banner表 服务实现类
 * </p>
 *
 * @author qzh
 * @since 2021-04-24
 */
@Service
public class CrmBannerServiceImpl extends ServiceImpl<CrmBannerMapper, CrmBanner> implements CrmBannerService {

    //查询所有banner
    //@Cacheable(value = "banner",key = "'selectIndexList'")
    @Override
    public List<CrmBanner> getAllBanner() {
        //查询前3张Banner
        QueryWrapper<CrmBanner> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("id");
        wrapper.last("limit 3");
        List<CrmBanner> list = baseMapper.selectList(wrapper);
        return list;
    }

}
