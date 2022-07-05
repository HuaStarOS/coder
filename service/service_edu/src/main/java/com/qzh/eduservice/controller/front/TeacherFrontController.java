package com.qzh.eduservice.controller.front;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qzh.eduservice.common.Result;
import com.qzh.eduservice.entity.EduCourse;
import com.qzh.eduservice.entity.EduTeacher;
import com.qzh.eduservice.service.EduCourseService;
import com.qzh.eduservice.service.EduTeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 *
 *@Author: qzh
 *@Date: 2022/4/13 17:13
 */
@RestController
//@CrossOrigin
@RequestMapping("/eduservice/teacherFront")
public class TeacherFrontController {

    @Autowired
    private EduTeacherService eduTeacherService;

    @Autowired
    private EduCourseService eduCourseService;


    /**
     * 前台系统分页查询讲师的方法
     * page：当前页 ，limit：显示记录数
     * @param page
     * @param limit
     * @return
     */
    @PostMapping("/getTeacherFrontPageList/{page}/{limit}")
    public Result getTeacherFrontPageList(@PathVariable(value = "page") Long page,@PathVariable(value = "limit") Long limit){
        Page<EduTeacher> teacherPage = new Page<>(page, limit);

        Map<String,Object> map = eduTeacherService.getTeacherFrontPageList(teacherPage);

        //返回分页中的所有数据
        return Result.ok().data(map);

    }
    /**
     * 根据id查询讲师信息（讲师本身信息+讲师所讲课程信息）
     */
    @GetMapping("/getTeacherFrontInfo/{id}")
    public Result getTeacherInfo(@PathVariable String id) {
        //查询讲师信息
        EduTeacher teacher = eduTeacherService.getById(id);

        //查询讲师所讲课程信息
        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();
        wrapper.eq("teacher_id", id);
        List<EduCourse> courseList = eduCourseService.list(wrapper);

        return Result.ok().data("teacher", teacher).data("courseList", courseList);
    }

}
