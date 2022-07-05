package com.qzh.eduservice.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qzh.eduservice.entity.EduTeacher;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 讲师 服务类
 * </p>
 *
 * @author qzh
 * @since 2021-03-20
 */
public interface EduTeacherService extends IService<EduTeacher> {

    Map<String, Object> getTeacherFrontPageList(Page<EduTeacher> teacherPage);
}
