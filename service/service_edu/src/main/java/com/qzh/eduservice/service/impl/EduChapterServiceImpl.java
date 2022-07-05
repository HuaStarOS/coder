package com.qzh.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.qzh.eduservice.entity.EduChapter;
import com.qzh.eduservice.entity.EduVideo;
import com.qzh.eduservice.entity.chapter.ChapterVo;
import com.qzh.eduservice.entity.chapter.VideoVo;
import com.qzh.eduservice.exception.CoderException;
import com.qzh.eduservice.mapper.EduChapterMapper;
import com.qzh.eduservice.service.EduChapterService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qzh.eduservice.service.EduVideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author qzh
 * @since 2021-04-14
 */
@Service
public class EduChapterServiceImpl extends ServiceImpl<EduChapterMapper, EduChapter> implements EduChapterService {

    @Autowired
    private EduVideoService eduVideoService;

    /**
     * 获取课程大纲列表，根据课程id进行查询
     *
     * @param courseId
     * @return
     */
    @Override
    public List<ChapterVo> getChapterVideoByCourseId(String courseId) {

        // 1.根据课程id查询该课程的所有章节
        QueryWrapper<EduChapter> chapterVoQueryWrapper = new QueryWrapper<>();
        chapterVoQueryWrapper.eq("course_id", courseId);
        chapterVoQueryWrapper.orderByAsc("sort");
        List<EduChapter> eduChapters = baseMapper.selectList(chapterVoQueryWrapper);

        // 创建finalChapterList用于封装最终结果
        List<ChapterVo> finalChapterList = new ArrayList<>();

        for (int i = 0; i < eduChapters.size(); i++) {
            ChapterVo chapterVo = new ChapterVo();
            EduChapter eduChapter = eduChapters.get(i);
            chapterVo.setId(eduChapter.getId());
            chapterVo.setTitle(eduChapter.getTitle());
            finalChapterList.add(chapterVo);
            // 根据章节id查询章节里面的的所有小节
            QueryWrapper<EduVideo> videoQueryWrapper = new QueryWrapper<>();
            videoQueryWrapper.eq("chapter_id", chapterVo.getId());
            videoQueryWrapper.eq("status", "Normal");
            videoQueryWrapper.orderByAsc("sort");
            List<EduVideo> eduVideoList = eduVideoService.list(videoQueryWrapper);
            // 创建finalVideoList用于封装小节最终结果
            List<VideoVo> finalVideoList = new ArrayList<>();

            for (int i1 = 0; i1 < eduVideoList.size(); i1++) {
                VideoVo videoVo = new VideoVo();
                EduVideo eduVideo = eduVideoList.get(i1);
                videoVo.setId(eduVideo.getId());
                videoVo.setTitle(eduVideo.getTitle());
                videoVo.setVideoSourceId(eduVideo.getVideoSourceId());
                videoVo.setFree(eduVideo.getIsFree());
                finalVideoList.add(videoVo);
            }
            chapterVo.setChildren(finalVideoList);

        }

        return finalChapterList;
    }

    @Override
    public List<ChapterVo> getAdminChapterVideoByCourseId(String courseId) {
        // 1.根据课程id查询该课程的所有章节
        QueryWrapper<EduChapter> chapterVoQueryWrapper = new QueryWrapper<>();
        chapterVoQueryWrapper.eq("course_id", courseId);
        chapterVoQueryWrapper.orderByAsc("sort");
        List<EduChapter> eduChapters = baseMapper.selectList(chapterVoQueryWrapper);

        // 创建finalChapterList用于封装最终结果
        List<ChapterVo> finalChapterList = new ArrayList<>();

        for (int i = 0; i < eduChapters.size(); i++) {
            ChapterVo chapterVo = new ChapterVo();
            EduChapter eduChapter = eduChapters.get(i);
            chapterVo.setId(eduChapter.getId());
            chapterVo.setTitle(eduChapter.getTitle());
            finalChapterList.add(chapterVo);
            // 根据章节id查询章节里面的的所有小节
            QueryWrapper<EduVideo> videoQueryWrapper = new QueryWrapper<>();
            videoQueryWrapper.eq("chapter_id", chapterVo.getId());
            videoQueryWrapper.orderByAsc("sort");
            List<EduVideo> eduVideoList = eduVideoService.list(videoQueryWrapper);
            // 创建finalVideoList用于封装小节最终结果
            List<VideoVo> finalVideoList = new ArrayList<>();

            for (int i1 = 0; i1 < eduVideoList.size(); i1++) {
                VideoVo videoVo = new VideoVo();
                EduVideo eduVideo = eduVideoList.get(i1);
                videoVo.setId(eduVideo.getId());
                videoVo.setTitle(eduVideo.getTitle());
                videoVo.setVideoSourceId(eduVideo.getVideoSourceId());
                videoVo.setFree(eduVideo.getIsFree());
                finalVideoList.add(videoVo);
            }
            chapterVo.setChildren(finalVideoList);
        }
        return finalChapterList;
    }

    /**
     * 删除章节，要先删除小节再删除章节
     *
     * @param chapterId
     * @return
     */
    @Override
    public boolean deleteChapter(String chapterId) {
        QueryWrapper<EduVideo> wrapper = new QueryWrapper<>();
        wrapper.eq("chapter_id", chapterId);
        // 查询小节，如果有小节数据则不进行删除。没有则进行删除
        int count = eduVideoService.count(wrapper);
        if (count > 0){
            throw new CoderException(20001, "不能删除");
        }else{
            int delete = baseMapper.deleteById(chapterId);
            return delete > 0;
        }
    }

    /**
     * 根据课程id删除章节部分
     * @param id
     */
    @Override
    public void removeChapterByCourseId(String id) {
        QueryWrapper<EduChapter> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id", id);
        baseMapper.delete(wrapper);
    }
}
