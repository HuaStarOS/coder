package com.qzh.vodtest;

import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadVideoRequest;
import com.aliyun.vod.upload.resp.UploadVideoResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.vod.model.v20170321.GetPlayInfoRequest;
import com.aliyuncs.vod.model.v20170321.GetPlayInfoResponse;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;

import java.util.List;

/**
 * @Author: qzh
 * @Date: 2021/4/23 11:08
 */
public class TestVod {

    public static void main(String[] args) throws ClientException {
        String accessKeyId = "LTAI5tAcZpgECBhdbEgszK5E";
        String accessKeySecret = "N4HTUMi2ko8YGRXTZkDyRTi7RHyIHj";
        // 文件标题
        String title = "6 - What If I Want to Move Faster - upload by sdk";
        // 本地文件上传和文件流上传时，文件名称为上传文件绝对路径，如:/User/sample/文件名称.mp4 。 文件名必须包含扩展名
        String fileName = "F:\\BaiduNetdiskDownload\\项目资料\\1-阿里云上传测试视频\\6 - What If I Want to Move Faster.mp4";
        testUploadVideo(accessKeyId, accessKeySecret, title, fileName);
        getPlayAuth();

    }

    // 根据视频id获取视频播放凭证
    public static void getPlayAuth() throws ClientException {
        // 1. 根据视频id获取视频播放凭证
        // 创建初始化对象
        DefaultAcsClient client = InitObject.initVodClient("LTAI5tAcZpgECBhdbEgszK5E", "N4HTUMi2ko8YGRXTZkDyRTi7RHyIHj");

        // c创建获取视频地址request和response
        GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
        GetVideoPlayAuthResponse response = new GetVideoPlayAuthResponse();

        // 向request对象里面设置视频id
        request.setVideoId("9259fd3d163f433892e0f7ec1bcf26ed");

        // 调用初始化对象里面的方法，传递request对象，获取播放凭证
        response = client.getAcsResponse(request);

        System.out.println(response.getPlayAuth());
    }


    // 根据视频id获取视频播放地址
    public static void getPlayUrl() throws ClientException {
        // 1. 根据视频id获取视频播放地址
        // 创建初始化对象
        DefaultAcsClient client = InitObject.initVodClient("LTAI5tAcZpgECBhdbEgszK5E", "N4HTUMi2ko8YGRXTZkDyRTi7RHyIHj");

        // c创建获取视频地址request和response
        GetPlayInfoRequest request = new GetPlayInfoRequest();
        GetPlayInfoResponse response = new GetPlayInfoResponse();

        // 向request对象里面设置视频id
        request.setVideoId("9259fd3d163f433892e0f7ec1bcf26ed");

        // 调用初始化对象里面的方法，传递request对象，获取数据
        response = client.getAcsResponse(request);

        List<GetPlayInfoResponse.PlayInfo> playInfoList = response.getPlayInfoList();

        // 播放地址
        for (GetPlayInfoResponse.PlayInfo playInfo : playInfoList) {
            System.out.println(playInfo.getPlayURL());
        }
        // Base信息
        System.out.println(response.getVideoBase().getTitle());
    }
    /**
     * 本地文件上传接口
     *
     * @param accessKeyId
     * @param accessKeySecret
     * @param title 视频标题(必选)
     * @param fileName  本地文件上传和文件流上传时，文件名称为上传文件绝对路径，如:/User/sample/文件名称.mp4 。 文件名必须包含扩展名
     */
    private static void testUploadVideo(String accessKeyId, String accessKeySecret, String title, String fileName) {
        UploadVideoRequest request = new UploadVideoRequest(accessKeyId, accessKeySecret, title, fileName);
        /* 可指定分片上传时每个分片的大小，默认为2M字节 */
        request.setPartSize(2 * 1024 * 1024L);
        /* 可指定分片上传时的并发线程数，默认为1，(注：该配置会占用服务器CPU资源，需根据服务器情况指定）*/
        request.setTaskNum(1);

        UploadVideoImpl uploader = new UploadVideoImpl();
        UploadVideoResponse response = uploader.uploadVideo(request);
        if (response.isSuccess()) {
            System.out.print("VideoId=" + response.getVideoId() + "\n");
        } else {
            /* 如果设置回调URL无效，不影响视频上传，可以返回VideoId同时会返回错误码。其他情况上传失败时，VideoId为空，此时需要根据返回错误码分析具体错误原因 */
            System.out.print("VideoId=" + response.getVideoId() + "\n");
            System.out.print("ErrorCode=" + response.getCode() + "\n");
            System.out.print("ErrorMessage=" + response.getMessage() + "\n");
        }
    }

}
