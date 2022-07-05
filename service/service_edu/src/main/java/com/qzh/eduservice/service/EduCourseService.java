package com.qzh.eduservice.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qzh.eduservice.entity.EduCourse;
import com.baomidou.mybatisplus.extension.service.IService;
import com.qzh.eduservice.entity.vo.CourseInfoVo;
import com.qzh.eduservice.entity.vo.CoursePublishVo;
import com.qzh.eduservice.entity.vo.CourseQuery;
import com.qzh.eduservice.entity.vo.frontVo.CourseFrontVo;
import com.qzh.eduservice.entity.vo.frontVo.CourseWebVo;

import java.util.Map;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author qzh
 * @since 2021-04-14
 */
public interface EduCourseService extends IService<EduCourse> {

    /**
     * 添加课程基本信息的方法
     * @param courseInfoVo
     * @return 课程id
     */
    String saveCourseInfo(CourseInfoVo courseInfoVo);

    /**
     * 根据课程id查询课程基本信息
     * @param courseId
     * @return
     */
    CourseInfoVo getCourseInfo(String courseId);

    /**
     * 修改课程基本信息的方法
     * @param courseInfoVo
     */
    void updateCourseInfo(CourseInfoVo courseInfoVo);

    /**
     * 根据课程id查询课程确认信息
     * @param id
     * @return
     */
    CoursePublishVo getPublishCourseInfo(String id);

    /**
     * 课程列表中删除课程方法
     * @param id
     * @return
     */
    boolean removeCourse(String id);

    /**
     * 前台条件查询课程列表带分页功能
     * @param pageCourse
     * @param courseFrontVo
     * @return
     */
    Map<String, Object> getCourseFrontList(Page<EduCourse> pageCourse, CourseFrontVo courseFrontVo);

    /**
     * 根据课程id查询课程的基本信息
     * @param courseId
     * @return
     */
    CourseWebVo getBaseCourseInfo(String courseId);
}
