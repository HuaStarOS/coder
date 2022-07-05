package com.qzh.eduservice.entity.chapter;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: qzh
 * @Date: 2021/4/15 14:54
 */
@Data
public class ChapterVo implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    private String title;

    /**
     * 表示小节
     */
    private List<VideoVo> children = new ArrayList<VideoVo>();

}

