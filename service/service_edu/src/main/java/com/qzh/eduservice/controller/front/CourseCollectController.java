package com.qzh.eduservice.controller.front;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qzh.eduservice.common.JwtUtils;
import com.qzh.eduservice.common.Result;
import com.qzh.eduservice.entity.CourseCollect;
import com.qzh.eduservice.service.CourseCollectService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Calendar;
import java.util.Map;

/**
 * <p>
 * 课程收藏 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2020-09-08
 */
@RestController
@RequestMapping("/eduservice/course-collect")
public class CourseCollectController {

    @Autowired
    CourseCollectService collectService;

    @ApiOperation(value = "添加收藏课程")
    @PostMapping("addCollect/{courseId}")
    public Result addCollect(@ApiParam(name = "courseCollect", value = "收藏对象", required = true)
        @PathVariable String courseId,HttpServletRequest request){
        String memmberId= JwtUtils.getMemberIdByJwtToken(request);
        if(memmberId==""){
            return Result.error().message("请先登陆");
        }else {
            CourseCollect courseCollect = new CourseCollect();
            courseCollect.setCourseId(courseId);
            courseCollect.setMemberId(memmberId);
            collectService.addone(courseCollect);
            return Result.ok().message("收藏成功");
        }
    }

    @ApiOperation(value = "删除收藏课程")
    @DeleteMapping("{courseId}")
    public Result deletCollect(@ApiParam(name = "courseCollect", value = "收藏对象", required = true)
                                      @PathVariable String courseId,HttpServletRequest request){
        String memmberId= JwtUtils.getMemberIdByJwtToken(request);
        if(memmberId==""){
            return Result.error().message("请先登陆");
        }else {
            CourseCollect courseCollect = new CourseCollect();
            courseCollect.setCourseId(courseId);
            courseCollect.setMemberId(memmberId);
            collectService.deletone(courseCollect);
            Calendar.DATE;
            return Result.ok();
        }

    }

    @ApiOperation(value = "查看是否收藏课程")
    @GetMapping("isCollect/{courseId}")
    public Result getCollect(@ApiParam(name = "course_id", value = "课程id", required = true)
                                @PathVariable String courseId, HttpServletRequest request){
        String memmberId= JwtUtils.getMemberIdByJwtToken(request);
        CourseCollect one = collectService.getOne(new QueryWrapper<CourseCollect>().eq("course_id", courseId)
                .eq("member_id", memmberId));
        System.out.println(one);
        if(one==null){
            return Result.ok().data("iscollect","false");
        }else {
            return Result.ok().data("iscollect","true");
        }

    }

    @ApiOperation(value = "获取用户收藏的课程")
    @PostMapping("Collect/{page}/{limit}")
    public Result Collect(@ApiParam(name = "page", value = "当前页码", required = true) @PathVariable Long page,
                          @ApiParam(name = "limit", value = "每页记录数", required = true) @PathVariable Long limit,
                          HttpServletRequest request){
        Page<CourseCollect> pageParam = new Page<CourseCollect>(page, limit);
        String memmberId= JwtUtils.getMemberIdByJwtToken(request);
        Map<String, Object> colletweb = collectService.getColletweb(pageParam, memmberId);
        return Result.ok().data(colletweb);
    }



}

