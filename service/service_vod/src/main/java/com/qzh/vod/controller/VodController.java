package com.qzh.vod.controller;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.vod.model.v20170321.DeleteVideoRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;
import com.qzh.vod.Utils.ConstantVodUtils;
import com.qzh.vod.Utils.InitVodClient;
import com.qzh.vod.common.Result;
import com.qzh.vod.exception.CoderException;
import com.qzh.vod.service.VodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @Author: qzh
 * @Date: 2021/4/23 14:48
 */
@RestController
@RequestMapping("/eduvod/video")
//@CrossOrigin
public class VodController {

    @Autowired
    private VodService vodService;

    /**
     * 上传视频到阿里云
     * @param file
     * @return
     */

    @PostMapping("/uploadAliyunVideo")
    public Result uploadAliyunVideo(MultipartFile file) {
        //返回上传视频的id
        String videoId = vodService.uploadVideoAliyun(file);
        System.out.println("上传的视频id" + videoId);
        return Result.ok().data("videoId", videoId);
    }

    /**
     * 删除阿里云中的视频
     * @param id    视频id
     * @return
     */
    @DeleteMapping("removeAliyunVideo/{id}")
    public Result removeAliyunVideo(@PathVariable("id") String id){
        try {
            // 初始化客户端对象
            DefaultAcsClient client = InitVodClient.initVodClient(ConstantVodUtils.ACCESS_KEY_ID, ConstantVodUtils.ACCESS_KEY_SECRET);
            // 创建删除视频request对象
            DeleteVideoRequest request = new DeleteVideoRequest();
            // 向request中设置视频id
            request.setVideoIds(id);
            // 调用客户端对象的方法实现删除
            client.getAcsResponse(request);
            return Result.ok();
        } catch (ClientException e) {
            e.printStackTrace();
            throw new CoderException(20001, "删除视频失败");
        }
    }

    /**
     * 根据id删除多个阿里云视频
     */
    @DeleteMapping("/removeBatch")
    public Result removeBatch(@RequestParam("videoIdList") List<String> videoIdList){
        vodService.removeMoreVideo(videoIdList);
        return Result.ok();
    }

    //根据视频id获取视频凭证
    @GetMapping("getPlayAuth/{id}")
    public Result getPlayAuth(@PathVariable String id) {
        try {
            //创建初始化对象
            DefaultAcsClient client =
                    InitVodClient.initVodClient(ConstantVodUtils.ACCESS_KEY_ID, ConstantVodUtils.ACCESS_KEY_SECRET);
            //创建获取凭证request和response对象
            GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
            //向request设置视频id
            request.setVideoId(id);
            //调用方法得到凭证
            GetVideoPlayAuthResponse response = client.getAcsResponse(request);
            String playAuth = response.getPlayAuth();
            return Result.ok().data("playAuth",playAuth);
        }catch(Exception e) {
            throw new CoderException(20001,"获取凭证失败");
        }
    }

}
