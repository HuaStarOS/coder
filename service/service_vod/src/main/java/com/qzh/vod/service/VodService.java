package com.qzh.vod.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;


/**
 * @Author: qzh
 * @Date: 2021/4/23 14:49
 */
public interface VodService {


    /**
     * 上传视频到阿里云
     * @param file
     * @return
     */
    String uploadVideoAliyun(MultipartFile file);

    /**
     * 删除多个视频
     * @param videoIdList
     */
    void removeMoreVideo(List<String> videoIdList);
}
