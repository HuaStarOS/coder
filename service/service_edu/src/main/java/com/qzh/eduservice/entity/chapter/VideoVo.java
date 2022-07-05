package com.qzh.eduservice.entity.chapter;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author: qzh
 * @Date: 2021/4/15 14:54
 */
@Data
public class VideoVo implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    private String title;

    private Boolean free;

    private String videoSourceId;

}

