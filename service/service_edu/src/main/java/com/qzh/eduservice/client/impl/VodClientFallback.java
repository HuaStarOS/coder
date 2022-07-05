package com.qzh.eduservice.client.impl;

import com.qzh.eduservice.client.VodClient;
import com.qzh.eduservice.common.Result;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author: qzh
 * @Date: 2021/4/24 13:13
 */
@Component
public class VodClientFallback implements VodClient {
    @Override
    public Result removeAliyunVideo(String id) {
        return Result.error().message("删除一个视频失败！");
    }

    @Override
    public Result removeBatch(List<String> videoIdList) {
        return Result.error().message("删除多个视频失败！");
    }
}
