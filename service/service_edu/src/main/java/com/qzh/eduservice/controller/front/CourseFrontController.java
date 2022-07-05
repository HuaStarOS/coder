package com.qzh.eduservice.controller.front;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qzh.eduservice.common.Result;
import com.qzh.eduservice.entity.EduCourse;
import com.qzh.eduservice.entity.EduTeacher;
import com.qzh.eduservice.entity.chapter.ChapterVo;
import com.qzh.eduservice.entity.vo.frontVo.CourseFrontVo;
import com.qzh.eduservice.entity.vo.frontVo.CourseWebVo;
import com.qzh.eduservice.service.EduChapterService;
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
@RequestMapping("/eduservice/courseFront")
public class CourseFrontController {

    @Autowired
    private EduCourseService courseService;

    @Autowired
    private EduChapterService chapterService;

    //1 条件查询带分页查询课程
    @PostMapping("getFrontCourseList/{page}/{limit}")
    public Result getFrontCourseList(@PathVariable(value = "page") long page, @PathVariable(value = "limit") long limit,
                                @RequestBody(required = false) CourseFrontVo courseFrontVo) {
        Page<EduCourse> pageCourse = new Page<>(page,limit);
        Map<String,Object> map = courseService.getCourseFrontList(pageCourse,courseFrontVo);
        //返回分页所有数据
        return Result.ok().data(map);
    }

    //2 课程详情的方法
    @GetMapping("getFrontCourseInfo/{courseId}")
    public Result getFrontCourseInfo(@PathVariable String courseId) {
        //根据课程id，编写sql语句查询课程信息
        CourseWebVo courseWebVo = courseService.getBaseCourseInfo(courseId);

        //根据课程id查询章节和小节
        List<ChapterVo> chapterVideoList = chapterService.getChapterVideoByCourseId(courseId);

        return Result.ok().data("courseWebVo",courseWebVo).data("chapterVideoList",chapterVideoList);
    }
}