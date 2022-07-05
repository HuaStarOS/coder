package com.qzh.eduservice.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qzh.eduservice.common.Result;
import com.qzh.eduservice.entity.EduTeacher;
import com.qzh.eduservice.entity.vo.TeacherQuery;
import com.qzh.eduservice.exception.CoderException;
import com.qzh.eduservice.service.EduTeacherService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author qzh
 * @since 2021-03-20
 */
@RestController
@RequestMapping("/eduservice/teacher")
//@CrossOrigin
public class EduTeacherController {

    /**
     * 注入teacherService
     */
    @Autowired
    private EduTeacherService teacherService;


    /**
     * 查询教师表中所有的数据
     *
     * @return
     */
    @ApiOperation(value = "查询教师表中所有的数据")
    @GetMapping("/findAll")
    public Result findAllTeacher() {
        // 调用service方法实现查询所有操作
        List<EduTeacher> list = teacherService.list(null);
        return Result.ok().data("items", list);
    }

    /**
     * 逻辑删除教师
     *
     * @param id
     * @return
     */
    @ApiOperation(value = "逻辑删除教师")
    @DeleteMapping("/{id}")
    public Result remove(
            @ApiParam(name = "id", value = "教师ID", required = true)
            @PathVariable("id") String id) {

        boolean removeById = teacherService.removeById(id);

        if (removeById) {
            return Result.ok();
        } else {
            return Result.error();
        }
    }

    /**
     * 分页查询功能
     *
     * @param current 当前页
     * @param limit   每页记录数
     * @return
     */
    @GetMapping("/pageTeacher/{current}/{limit}")
    public Result pageListTeacher(@PathVariable("current") Long current,
                                  @PathVariable("limit") Long limit) {

        // 创建page对象
        Page<EduTeacher> pageTeacher = new Page<>(current, limit);
        // 调用方法实现分页
        // 调用方法时，会将的当前页的所有数据封装到 pageTeacher 对象中
        teacherService.page(pageTeacher, null);

        // 总记录数
        long total = pageTeacher.getTotal();
        // 当前页数据
        List<EduTeacher> records = pageTeacher.getRecords();

        return Result.ok().data("total", total).data("rows", records);
    }


    /**
     *  分页查询功能 带条件
     * @param current
     * @param limit
     * @param teacherQuery
     * @return
     */
    @PostMapping("/pageTeacherCondition/{current}/{limit}")
    public Result pageTeacherCondition(@PathVariable("current") Long current, @PathVariable("limit") Long limit,
                                       @RequestBody(required = false) TeacherQuery teacherQuery) {

        // 创建page对象
        Page<EduTeacher> pageTeacher = new Page<>(current, limit);
        System.out.println(teacherQuery);
        // 构建条件
        QueryWrapper<EduTeacher> wrapper = new QueryWrapper<>();
        // 多条件组合查询
        String name = teacherQuery.getName();
        Integer level = teacherQuery.getLevel();
        String begin = teacherQuery.getBegin();
        String end = teacherQuery.getEnd();
        // 判断条件值是否为空，如果不为空拼接条件
        if (!StringUtils.isEmpty(name)) {
            wrapper.like("name", name);
        }
        if (!StringUtils.isEmpty(level)) {
            wrapper.eq("level", level);
        }
        if (!StringUtils.isEmpty(begin)) {
            wrapper.ge("gmt_create", begin);
        }
        if (!StringUtils.isEmpty(end)) {
            wrapper.le("gmt_modified", end);
        }

        // 排序
        wrapper.orderByDesc("gmt_modified");
        // 调用方法实现分页
        // 调用方法时，会将的当前页的所有数据封装到 pageTeacher 对象中
        teacherService.page(pageTeacher, wrapper);

        // 总记录数
        long total = pageTeacher.getTotal();
        // 当前页数据
        List<EduTeacher> records = pageTeacher.getRecords();

        return Result.ok().data("total", total).data("rows", records);
    }

    /**
     * 添加教师接口的方法
     *
     * @param eduTeacher
     * @return
     */
    @PostMapping("/addTeacher")
    public Result addTeacher(@RequestBody EduTeacher eduTeacher) {
        boolean save = teacherService.save(eduTeacher);
        if (save) {
            return Result.ok();
        } else {
            return Result.error();
        }
    }

    /**
     * 根据id进行查询
     *
     * @param id
     * @return
     */
    @GetMapping("/getTeacher/{id}")
    public Result getTeacherById(@PathVariable("id") String id) {
        EduTeacher byId = teacherService.getById(id);
        try {
            //int i = 10/0;
        } catch (Exception e) {
            throw new CoderException(2002, "执行了自定义异常处理");
        }
        return Result.ok().data("teacher", byId);
    }

    @PostMapping("/updateTeacher")
    public Result updateTeacher(@RequestBody EduTeacher eduTeacher) {
        boolean flag = teacherService.updateById(eduTeacher);
        if (flag) {
            return Result.ok();
        } else {
            return Result.error();
        }
    }


}

