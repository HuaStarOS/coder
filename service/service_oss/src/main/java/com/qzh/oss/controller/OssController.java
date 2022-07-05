package com.qzh.oss.controller;

import com.qzh.oss.common.Result;
import com.qzh.oss.service.OssService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;


/**
 * @Author: qzh
 * @Date: 2021/4/8 16:25
 */
@RestController
@RequestMapping("/eduoss/file")
//@CrossOrigin
public class OssController {

    @Autowired
    private OssService ossService;

    /**
     * 上传头像的方法
     * @param file
     * @return
     */
    @PostMapping
    public Result uploadOssFile(MultipartFile file){
        // 获取上传文件 MultipartFile
        // 返回上传到OSS的路径
        String url = ossService.uploadFileAvatar(file);
        return Result.ok().data("url",url);
    }

}
