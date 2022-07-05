package com.qzh.eduservice.entity.subject;

import lombok.Data;

/**
 * @Author: qzh
 * @Date: 2021/4/13 10:35
 * 这是用于返回前端数据的实体类，二级分类
 */
@Data
public class TwoSubject {

    private String id;

    private String title;

    private String parentId;

    private Integer sort;

}
