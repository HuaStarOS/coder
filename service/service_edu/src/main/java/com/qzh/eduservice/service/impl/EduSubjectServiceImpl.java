package com.qzh.eduservice.service.impl;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.qzh.eduservice.entity.EduSubject;
import com.qzh.eduservice.entity.excel.SubjectData;
import com.qzh.eduservice.entity.subject.OneSubject;
import com.qzh.eduservice.entity.subject.TwoSubject;
import com.qzh.eduservice.listener.SubjectExcelListener;
import com.qzh.eduservice.mapper.EduSubjectMapper;
import com.qzh.eduservice.service.EduSubjectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author qzh
 * @since 2021-04-10
 */
@Service
public class EduSubjectServiceImpl extends ServiceImpl<EduSubjectMapper, EduSubject> implements EduSubjectService {

    /**
     * 添加课程分类
     *
     * @param file
     * @param subjectService
     */
    @Override
    public void saveSubject(MultipartFile file, EduSubjectService subjectService) {
        try {
            // 获取文件输入流
            InputStream inputStream = file.getInputStream();
            // 调用方法进行读取
            EasyExcel.read(inputStream, SubjectData.class, new SubjectExcelListener(subjectService)).sheet().doRead();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * 获取课程分类列表数据
     *
     * @return
     */
    @Override
    public List<OneSubject> getAllOneTwoSubject() {

        // 1. 查询所有一级分类，parent_id=0
        QueryWrapper<EduSubject> wrapperOne = new QueryWrapper<>();
        wrapperOne.eq("parent_id", "0");
        List<EduSubject> oneSubjectList = baseMapper.selectList(wrapperOne);

        // 2. 查询所有二级分类，parent_id!=0
        QueryWrapper<EduSubject> wrapperTwo = new QueryWrapper<>();
        wrapperOne.ne("parent_id", "0");
        List<EduSubject> twoSubjectList = baseMapper.selectList(wrapperTwo);

        // 创建list集合，用于存储最终的数据
        List<OneSubject> finalSubjectList = new ArrayList<>();

        // 3. 封装一级分类
        // 查询出来所有的一级分类list集合遍历，得到每个一级分类对象，获取每个一级分类对象值
        // 封装到要求的最终list集合中
        for (int i = 0; i < oneSubjectList.size(); i++) {
            // 得到oneSubjectList中的每个eduSubject对象
            EduSubject eduSubject = oneSubjectList.get(i);
            // 将eduSubject对象封装到finalSubjectList中
            OneSubject oneSubject = new OneSubject();
            BeanUtils.copyProperties(eduSubject, oneSubject);
            finalSubjectList.add(oneSubject);
            // 4. 封装二级分类
            for (int j = 0; j < twoSubjectList.size(); j++) {
                // 得到oneSubjectList中的每个eduSubject对象
                EduSubject eduTwoSubject = twoSubjectList.get(j);
                // 判断二级分类parent_id和一级分类id是否一样
                if (eduTwoSubject.getParentId().equals(oneSubject.getId())) {
                    TwoSubject twoSubject = new TwoSubject();
                    BeanUtils.copyProperties(eduTwoSubject, twoSubject);
                    // 把一级下面所有二级分类放到oneSubject里面
                    oneSubject.getChildren().add(twoSubject);
                }
            }
        }

        return finalSubjectList;
    }
}
