package com.qzh.vod.service.impl;

import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadStreamRequest;
import com.aliyun.vod.upload.resp.UploadStreamResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.vod.model.v20170321.DeleteVideoRequest;
import com.qzh.vod.Utils.ConstantVodUtils;
import com.qzh.vod.Utils.InitVodClient;
import com.qzh.vod.exception.CoderException;
import com.qzh.vod.service.VodService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * @Author: qzh
 * @Date: 2021/4/23 14:50
 */
@Service
public class VodServiceImpl implements VodService {

    @Override
    public String uploadVideoAliyun(MultipartFile file) {
        try {
            // fileName: 上传文件原始名称
            String fileName = file.getOriginalFilename();
            // title: 上传之后的显示的名称
            String title = fileName.substring(0,fileName.lastIndexOf("."));
            // inputStream: 上传文件输入流
            InputStream inputStream = file.getInputStream();
            // 上传视频
            UploadStreamRequest request = new UploadStreamRequest(ConstantVodUtils.ACCESS_KEY_ID, ConstantVodUtils.ACCESS_KEY_SECRET, title, fileName, inputStream);
            UploadVideoImpl uploader = new UploadVideoImpl();
            UploadStreamResponse response = uploader.uploadStream(request);

            String videoId = null;
            if (response.isSuccess()) {
                videoId = response.getVideoId();
            } else { //如果设置回调URL无效，不影响视频上传，可以返回VideoId同时会返回错误码。其他情况上传失败时，VideoId为空，此时需要根据返回错误码分析具体错误原因
                videoId = response.getVideoId();
            }
            return videoId;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void removeMoreVideo(List<String> videoIdList) {
        // 获取多个视频id
        String join = StringUtils.join(videoIdList.toArray(), ",");
        try {
            // 初始化客户端对象
            DefaultAcsClient client = InitVodClient.initVodClient(ConstantVodUtils.ACCESS_KEY_ID, ConstantVodUtils.ACCESS_KEY_SECRET);
            // 创建删除视频request对象
            DeleteVideoRequest request = new DeleteVideoRequest();
            // 向request中设置视频id
            request.setVideoIds(join);
            // 调用客户端对象的方法实现删除
            client.getAcsResponse(request);
        } catch (ClientException e) {
            e.printStackTrace();
            throw new CoderException(20001, "删除视频失败");
        }
    }
}
