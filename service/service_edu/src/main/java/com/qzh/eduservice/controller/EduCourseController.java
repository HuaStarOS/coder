package com.qzh.eduservice.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qzh.eduservice.common.Result;
import com.qzh.eduservice.entity.EduCourse;
import com.qzh.eduservice.entity.EduSubject;
import com.qzh.eduservice.entity.EduTeacher;
import com.qzh.eduservice.entity.vo.CourseInfoVo;
import com.qzh.eduservice.entity.vo.CoursePublishVo;
import com.qzh.eduservice.entity.vo.CourseQuery;
import com.qzh.eduservice.service.EduCourseService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.sql.ResultSet;
import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author qzh
 * @since 2021-04-14
 */
@RestController
@RequestMapping("/eduservice/course")
//@CrossOrigin
public class EduCourseController {

    @Autowired
    private EduCourseService courseService;

    /**
     * 课程列表
     * @return
     */
    @GetMapping("/getCourseList")
    public Result getCourseList(){
        List<EduCourse> list = courseService.list(null);
        return Result.ok().data("list", list);
    }


    /**
     * 多条件查询课程带分页
     */
    @ApiOperation(value = "多条件查询课程带分页")
    @PostMapping("/pageCourseCondition/{page}/{limit}")
    public Result pageCourseCondition(@ApiParam(name = "page", value = "当前页码", required = true)@PathVariable Long page,
                                 @ApiParam(name = "limit", value = "每页记录数", required = true)@PathVariable Long limit,
                                 @RequestBody(required = false) CourseQuery courseQuery){//通过封装courseQuery对象来直接传递查询条件
        //创建分页page对象
        Page<EduCourse> pageCourse = new Page<>(page, limit);

        //调用方法实现多条件分页查询
        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();
        // 多条件组合查询
        String title = courseQuery.getTitle();
        Integer status = courseQuery.getStatus();
        String begin = courseQuery.getBegin();
        String end = courseQuery.getEnd();
        // 判断条件值是否为空，如果不为空拼接条件
        if (!StringUtils.isEmpty(title)) {
        wrapper.like("title", title);
        }
        if (!StringUtils.isEmpty(status)) {
        wrapper.eq("status", status == 1 ? "Normal" : "Draft");
        }
        if (!StringUtils.isEmpty(begin)) {
        wrapper.ge("gmt_create", begin);
        }
        if (!StringUtils.isEmpty(end)) {
        wrapper.le("gmt_modified", end);
        }

        // 排序
        wrapper.orderByDesc("gmt_modified");
        //查询
        courseService.page(pageCourse, wrapper);
        List<EduCourse> records = pageCourse.getRecords();
        long total = pageCourse.getTotal();
        return Result.ok().data("total",total).data("rows",records);
    }

    /**
     * 添加课程基本信息的方法
     * @param courseInfoVo
     */
    @PostMapping("/addCourseInfo")
    public Result addCourseInfo(@RequestBody CourseInfoVo courseInfoVo){
        // 添加课程,并返回id
        String id = courseService.saveCourseInfo(courseInfoVo);
        return Result.ok().data("courseId",id);
    }

    /**
     * 根据课程id查询课程基本信息
     * @param courseId
     * @return
     */
    @GetMapping("/getCourseInfo/{courseId}")
    public Result getCourseInfo(@PathVariable("courseId") String  courseId){
        CourseInfoVo courseInfoVo = courseService.getCourseInfo(courseId);
        return Result.ok().data("courseInfoVo",courseInfoVo);
    }

    /**
     * 修改课程基本信息的方法
     * @param courseInfoVo
     */
    @PostMapping("/updateCourseInfo")
    public Result updateCourseInfo(@RequestBody CourseInfoVo courseInfoVo){
        // 添加课程,并返回id
        courseService.updateCourseInfo(courseInfoVo);
        return Result.ok();
    }

    /**
     * 根据课程id查询课程确认信息
     * @param id
     * @return
     */
    @GetMapping("getPublishCourseInfo/{id}")
    public Result getPublishCourseInfo(@PathVariable("id") String id){
        CoursePublishVo coursePublishVo = courseService.getPublishCourseInfo(id);
        return Result.ok().data("publish", coursePublishVo);
    }

    /**
     *  课程最终发布，修改课程状态
     * @param id
     * @return
     */
    @PostMapping("publishCourseInfo/{id}")
    public Result publishCourseInfo(@PathVariable("id") String  id){
        EduCourse eduCourse = new EduCourse();
        eduCourse.setId(id);
        eduCourse.setIsDeleted(0);
        eduCourse.setStatus("Normal");
        courseService.updateById(eduCourse);
        return Result.ok();
    }

    /**
     * 课程列表中删除课程方法
     * @param id
     * @return
     */
    @DeleteMapping("/removeCourseById/{id}")
    public Result removeCourseById(@PathVariable String id){
        boolean flag = courseService.removeCourse(id);
        if (flag){
            return Result.ok();
        }else {
            return Result.error();
        }
    }

}

