package com.qzh.eduservice.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.qzh.eduservice.common.Result;
import com.qzh.eduservice.entity.EduSubject;
import com.qzh.eduservice.entity.subject.OneSubject;
import com.qzh.eduservice.entity.vo.SubjectVo;
import com.qzh.eduservice.service.EduSubjectService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author qzh
 * @since 2021-04-10
 */
@RestController
@RequestMapping("/eduservice/subject")
//@CrossOrigin
public class EduSubjectController {

    @Autowired
    private EduSubjectService subjectService;

    /**
     * 添加课程分类
     * 获取上传过来的文件，把文件内容读取出来
     *
     * @param file
     * @return
     */
    @PostMapping("/addSubject")
    public Result addSubject(MultipartFile file) {
        // 调用subjectService的方法添加课程分类
        subjectService.saveSubject(file, subjectService);
        return Result.ok();
    }

    /**
     * 获取课程分类列表数据
     * @return
     */
    @GetMapping("/getAllSubject")
    public Result getAllSubject() {
        List<OneSubject> list = subjectService.getAllOneTwoSubject();
        return Result.ok().data("list",list);
    }

    /**
     * 添加一级分类
     * @param oneSubjectVo
     * @return
     */
    @PostMapping("/addOneSubject")
    public Result addOneSubject(@RequestBody SubjectVo oneSubjectVo) {
        EduSubject subject = new EduSubject();
        BeanUtils.copyProperties(oneSubjectVo,subject);
        subject.setGmtCreate(new Date());
        subject.setGmtModified(new Date());
        subject.setParentId("0");
        subjectService.save(subject);
        return Result.ok();
    }

    /**
     * 添加二级分类
     * @param twoSubjectVo
     * @return
     */
    @PostMapping("/addTwoSubject")
    public Result addTwoSubject(@RequestBody SubjectVo twoSubjectVo) {
        EduSubject subject = new EduSubject();
        BeanUtils.copyProperties(twoSubjectVo,subject);
        subject.setGmtCreate(new Date());
        subject.setGmtModified(new Date());
        subjectService.save(subject);
        return Result.ok();
    }

    @DeleteMapping("/remove/{parentId}/{id}")
    public Result remove(@PathVariable("id") String id,@PathVariable("parentId") String parentId) {
        boolean removeById = subjectService.removeById(id);
        // 如果是一级分类就还有删除所有的二级分类
        if ("0".equals(parentId)) {
            QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
            wrapper.eq("parent_id", id);
            removeById = subjectService.remove(wrapper);
        }
        if (removeById) {
            return Result.ok();
        } else {
            return Result.error();
        }
    }

    @PostMapping("/updateSubject")
    public Result updateSubject(@RequestBody EduSubject subject) {
        EduSubject eduSubject = subjectService.getById(subject.getId());
        eduSubject.setGmtModified(new Date());
        eduSubject.setSort(subject.getSort());
        eduSubject.setTitle(subject.getTitle());
        boolean flag = subjectService.updateById(eduSubject);
        if (flag) {
            return Result.ok();
        } else {
            return Result.error();
        }
    }
}

