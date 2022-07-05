package com.qzh.eduservice.controller;


import com.baomidou.mybatisplus.extension.api.R;
import com.qzh.eduservice.common.Result;
import com.qzh.eduservice.entity.EduChapter;
import com.qzh.eduservice.entity.chapter.ChapterVo;
import com.qzh.eduservice.service.EduChapterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
@RequestMapping("/eduservice/chapter")
//@CrossOrigin
public class EduChapterController {

    @Autowired
    private EduChapterService chapterService;

    /**
     * 获取课程大纲列表，根据课程id进行查询
     * @param courseId
     * @return
     */
    @GetMapping("/getChapterVideo/{courseId}")
    public Result getChapterVideo(@PathVariable String courseId){
        List<ChapterVo> list = chapterService.getAdminChapterVideoByCourseId(courseId);
        return Result.ok().data("allChapterVideo",list);
    }

    /**
     * 添加章节
     * @param eduChapter
     * @return
     */
    @PostMapping("/addChapter")
    public Result addChapter(@RequestBody EduChapter eduChapter){
        chapterService.save(eduChapter);
        return Result.ok();
    }

    /**
     * 根据章节id查询
     */
    @GetMapping("/getChapter/{chapterId}")
    public Result getChapter(@PathVariable String chapterId){
        EduChapter eduChapter = chapterService.getById(chapterId);
        return Result.ok().data("chapter",eduChapter);
    }

    /**
     * 修改章节
     * @param eduChapter
     * @return
     */
    @PostMapping("/updateChapter")
    public Result updateChapter(@RequestBody EduChapter eduChapter){
        chapterService.updateById(eduChapter);
        return Result.ok();
    }

    /**
     * 删除章节
     * @param chapterId
     * @return
     */
    @DeleteMapping("/deleteById/{chapterId}")
    public Result deleteById(@PathVariable String chapterId){
        boolean flag = chapterService.deleteChapter(chapterId);
        if (flag){
            return Result.ok();
        }else {
            return Result.error();
        }

    }

}

