package com.qzh.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qzh.eduservice.entity.CourseCollect;
import com.qzh.eduservice.entity.EduCourse;
import com.qzh.eduservice.mapper.CourseCollectMapper;
import com.qzh.eduservice.service.CourseCollectService;
import com.qzh.eduservice.service.EduCommentService;
import com.qzh.eduservice.service.EduCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程收藏 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2020-09-08
 */
@Service
public class CourseCollectServiceImpl extends ServiceImpl<CourseCollectMapper, CourseCollect> implements CourseCollectService {

    @Autowired
    EduCourseService eduCourseService;

    @Autowired
    EduCommentService eduCommentService;


    @Override
    public int addone(CourseCollect courseCollect) {
        int insert = this.baseMapper.insert(courseCollect);
        return insert;
    }

    @Override
    public void deletone(CourseCollect courseCollect) {
        int delete = this.baseMapper.delete(new QueryWrapper<CourseCollect>().eq("course_id", courseCollect.getCourseId())
                .eq("member_id", courseCollect.getMemberId()));
    }

    @Override
    public Map<String, Object> getColletweb(Page<CourseCollect> pageParam, String memmberId) {
        QueryWrapper<CourseCollect> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("member_id",memmberId);
        this.baseMapper.selectPage(pageParam, queryWrapper);
        List<CourseCollect> records = pageParam.getRecords();
        List<EduCourse> courses=new ArrayList<EduCourse>();
        for (CourseCollect c:records) {
            EduCourse byId = eduCourseService.getById(c.getCourseId());
            courses.add(byId);
        }
        long current = pageParam.getCurrent();
        long pages = pageParam.getPages();
        long size = pageParam.getSize();
        long total = pageParam.getTotal();
        boolean hasNext = pageParam.hasNext();
        boolean hasPrevious = pageParam.hasPrevious();

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("items", courses);
        map.put("current", current);
        map.put("pages", pages);
        map.put("size", size);
        map.put("total", total);
        map.put("hasNext", hasNext);
        map.put("hasPrevious", hasPrevious);
        return map;

    }
}
