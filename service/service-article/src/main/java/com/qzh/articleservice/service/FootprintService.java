package com.qzh.articleservice.service;

import com.qzh.articleservice.entity.FootVo;
import com.qzh.articleservice.entity.Footprint;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author testjava
 * @since 2020-09-17
 */
public interface FootprintService extends IService<Footprint> {

    List<FootVo> getAll();
}
