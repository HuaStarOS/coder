package com.qzh.oss.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * @Author: qzh
 * @Date: 2021/4/8 16:25
 */
public interface OssService {
    /**
     * 上传头像到oss
     * @param file
     * @return
     */
    String uploadFileAvatar(MultipartFile file);
}
