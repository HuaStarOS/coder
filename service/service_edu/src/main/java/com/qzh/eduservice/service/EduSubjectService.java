package com.qzh.eduservice.service;

import com.qzh.eduservice.entity.EduSubject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.qzh.eduservice.entity.subject.OneSubject;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 服务类
 * </p>
 *
 * @author qzh
 * @since 2021-04-10
 */
public interface EduSubjectService extends IService<EduSubject> {

    /**
     * 添加课程分类
     * @param file
     * @param subjectService
     */
    void saveSubject(MultipartFile file, EduSubjectService subjectService);

    /**
     * 获取课程分类列表数据
     * @return
     */
    List<OneSubject> getAllOneTwoSubject();
}
