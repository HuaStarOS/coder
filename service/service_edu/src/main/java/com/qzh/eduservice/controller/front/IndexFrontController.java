package com.qzh.eduservice.controller.front;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.qzh.eduservice.common.Result;
import com.qzh.eduservice.entity.EduCourse;
import com.qzh.eduservice.entity.EduTeacher;
import com.qzh.eduservice.service.EduCourseService;
import com.qzh.eduservice.service.EduTeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author: qzh
 * @Date: 2021/4/28 13:44
 */
@RestController
//@CrossOrigin
@RequestMapping("/eduservice/indexFront")
public class IndexFrontController {

    @Autowired
    private EduCourseService eduCourseService;

    @Autowired
    private EduTeacherService eduTeacherService;

    /**
     * 查询前8条热门课程，查询前4个名师
     */
    @GetMapping("/index")
    public Result index(){
        //查询前8条热门课程
        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("view_count");
        wrapper.last("limit 8");
        List<EduCourse> courseList = eduCourseService.list(wrapper);

        //查询前4张名师
        QueryWrapper<EduTeacher> wrapper1 = new QueryWrapper<>();
        wrapper1.orderByDesc("id");
        wrapper1.last("limit 4");
        List<EduTeacher> teacherList = eduTeacherService.list(wrapper1);


        return Result.ok().data("courseList",courseList).data("teacherList",teacherList);
    }

}
