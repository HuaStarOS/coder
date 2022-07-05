package com.qzh.oss.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.qzh.oss.service.OssService;
import com.qzh.oss.utils.ConstantPropertiesUtils;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.UUID;

/**
 * @Author: qzh
 * @Date: 2021/4/8 16:25
 */
@Service
public class OssServiceImpl implements OssService {

    /**
     * 上传头像到oss
     * @param file
     * @return
     */
    @Override
    public String uploadFileAvatar(MultipartFile file) {

       // 获取OSS的基本信息：节点、密钥、ID
        String endpoint = ConstantPropertiesUtils.END_POINT;
        String accessKeyId = ConstantPropertiesUtils.KEY_ID;
        String accessKeySecret = ConstantPropertiesUtils.KEY_SECRET;
        String bucketName = ConstantPropertiesUtils.BUCKET_NAME;
        System.out.println(endpoint);

        try {
            // 创建OSSClient实例。
            OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

            // 获取上传文件流。
            InputStream inputStream = file.getInputStream();

            // 获取文件名称
            String fileName = file.getOriginalFilename();

            // 1、在文件名称里面添加随机唯一的值，避免出现重复文件名，导致文件被覆盖
            String uuid = UUID.randomUUID().toString().replaceAll("-", "");
            fileName = uuid + fileName;

            // 2、把文件按照日期进行分类
            String dataPath = new DateTime().toString("yyyy/MM/dd");
            fileName = dataPath + "/" + fileName;

            // 调用OSS方法实现上传
            // 第一个参数：Bucket名称，相当于存到的哪个文件夹下
            // 第二个参数：上传到oss的文件路径和文件名称(绝对路径) 比如 aa/bb/1.jpg
            // 第三个参数：上传文件输入流
            ossClient.putObject(bucketName, fileName, inputStream);

            // 关闭OSSClient。
            ossClient.shutdown();

            // 把上传之后的文件路径返回，没有办法直接获取需要把oss路径手动拼接出来
            String url = "https://"+bucketName+"."+endpoint+"/"+fileName;

            return url;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
