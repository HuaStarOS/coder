package com.qzh.eduservice.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.qzh.eduservice.entity.CourseCollect;

import java.util.Map;

/**
 * <p>
 * 课程收藏 服务类
 * </p>
 *
 * @author testjava
 * @since 2020-09-08
 */
public interface CourseCollectService extends IService<CourseCollect> {

    int addone(CourseCollect courseCollect);

    void deletone(CourseCollect courseCollect);

    Map<String, Object> getColletweb(Page<CourseCollect> pageParam, String memmberId);
}
