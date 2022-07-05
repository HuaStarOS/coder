package com.qzh.eduservice.client;

import com.qzh.eduservice.client.impl.VodClientFallback;
import com.qzh.eduservice.common.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @Author: qzh
 * @Date: 2021/4/23 19:32
 */
@Component
@FeignClient(value = "service-vod",fallback = VodClientFallback.class)
public interface VodClient {

    /**
     * 调用service-vod模块里面的方法
     * 删除阿里云视频
     * @param id
     * @return
     */
    @DeleteMapping("/eduvod/video/removeAliyunVideo/{id}")
    public Result removeAliyunVideo(@PathVariable("id") String id);

    /**
     *  根据id删除多个阿里云视频
     * @param videoIdList  多个视频id
     * @return
     */
    @DeleteMapping("/eduvod/video/removeBatch")
    public Result removeBatch(@RequestParam("videoIdList") List<String> videoIdList);

}
