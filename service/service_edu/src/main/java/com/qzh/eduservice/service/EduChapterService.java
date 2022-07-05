package com.qzh.eduservice.service;

import com.qzh.eduservice.entity.EduChapter;
import com.baomidou.mybatisplus.extension.service.IService;
import com.qzh.eduservice.entity.chapter.ChapterVo;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author qzh
 * @since 2021-04-14
 */
public interface EduChapterService extends IService<EduChapter> {

    /**
     * 获取课程大纲列表，根据课程id进行查询
     * @param courseId
     * @return
     */
    List<ChapterVo> getChapterVideoByCourseId(String courseId);

    List<ChapterVo> getAdminChapterVideoByCourseId(String courseId);
    /**
     * 删除章节，要先删除小节再删除章节
     * @param chapterId
     * @return
     */
    boolean deleteChapter(String chapterId);

    /**
     * 根据课程id删除章节部分
     * @param id
     */
    void removeChapterByCourseId(String id);
}
