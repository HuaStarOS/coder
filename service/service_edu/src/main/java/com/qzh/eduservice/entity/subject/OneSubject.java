package com.qzh.eduservice.entity.subject;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: qzh
 * @Date: 2021/4/13 10:35
 * 这是用于返回前端数据的实体类，一级分类
 */
@Data
public class OneSubject {

    private String id;

    private String title;

    private String parentId;

    private Integer sort;

    /**
     * 一个一级分类有多个二级分类
     */
    private List<TwoSubject> children = new ArrayList<>();
}
