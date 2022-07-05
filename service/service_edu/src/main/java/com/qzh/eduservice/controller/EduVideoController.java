package com.qzh.eduservice.controller;


import com.qzh.eduservice.common.Result;
import com.qzh.eduservice.entity.EduVideo;
import com.qzh.eduservice.service.EduVideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *
 * @authoR qzh
 * @since 2021-04-14
 */
@RestController
@RequestMapping("/eduservice/video")
//@CrossOrigin
public class EduVideoController {

    @Autowired
    private EduVideoService eduVideoService;

    //添加小节
    @PostMapping("/addVideo")
    public Result addVideo(@RequestBody EduVideo eduVideo){
        if (StringUtils.isEmpty(eduVideo.getVideoSourceId())) {
            eduVideo.setStatus("Empty");
        }else {
            eduVideo.setStatus("Normal");
        }
        eduVideoService.save(eduVideo);
        return Result.ok();
    }


    //删除小节并且删除视频
    @DeleteMapping("/deleteVideo/{id}")
    public Result deleteVideo(@PathVariable String id){
        eduVideoService.removeVideoById(id);
        return Result.ok();
    }

    //修改小节
    @PostMapping("/updateVideo")
    public Result updateVideo(@RequestBody EduVideo eduVideo){
        System.out.println(eduVideo.getVideoSourceId());
        if (StringUtils.isEmpty(eduVideo.getVideoSourceId())) {
            eduVideo.setStatus("Empty");
        }else {
            eduVideo.setStatus("Normal");
        }
        eduVideoService.updateById(eduVideo);
        return Result.ok();
    }

    //根据小节id查询
    @GetMapping("/getVideoById/{videoId}")
    public Result getVideoById(@PathVariable String videoId){
        EduVideo eduVideo = eduVideoService.getById(videoId);
        return Result.ok().data("video",eduVideo);
    }


}




