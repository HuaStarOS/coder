package com.qzh.eduservice.mapper;

import com.qzh.eduservice.entity.EduCourse;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qzh.eduservice.entity.vo.CoursePublishVo;
import com.qzh.eduservice.entity.vo.frontVo.CourseWebVo;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author qzh
 * @since 2021-04-14
 */
public interface EduCourseMapper extends BaseMapper<EduCourse> {

    /**
     * 获取课程发布的信息
     * @param courseId
     * @return
     */
    CoursePublishVo getPublishCourseInfo(String courseId);

    /**
     * 根据课程id查询课程的基本信息
     * @param courseId
     * @return
     */
    CourseWebVo getBaseCourseInfo(String courseId);
}
