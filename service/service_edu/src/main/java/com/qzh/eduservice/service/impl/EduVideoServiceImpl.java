package com.qzh.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.qzh.eduservice.client.VodClient;
import com.qzh.eduservice.common.Result;
import com.qzh.eduservice.entity.EduVideo;
import com.qzh.eduservice.exception.CoderException;
import com.qzh.eduservice.mapper.EduVideoMapper;
import com.qzh.eduservice.service.EduVideoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程视频 服务实现类
 * </p>
 *
 * @author qzh
 * @since 2021-04-14
 */
@Service
public class EduVideoServiceImpl extends ServiceImpl<EduVideoMapper, EduVideo> implements EduVideoService {

    @Autowired
    private VodClient vodClient;

    /**
     * 根据课程id删除小节
     * TODO 删除小节，视频也要删除
     * @param id
     */
    @Override
    public void removeVideoByCourseId(String id) {

        QueryWrapper<EduVideo> wrapperVideo = new QueryWrapper<>();
        wrapperVideo.eq("course_id", id);
        wrapperVideo.select("video_source_id");
        // 获取该课程的多个小节视频id
        List<String> videoIds = new ArrayList<>();
        List<EduVideo> eduVideos = baseMapper.selectList(wrapperVideo);

        for (EduVideo eduVideo : eduVideos) {
            String videoSourceId = eduVideo.getVideoSourceId();
            if (!StringUtils.isEmpty(videoSourceId)) {
                videoIds.add(videoSourceId);
            }
        }

        if (videoIds.size()>0){
            vodClient.removeBatch(videoIds);
        }

        QueryWrapper<EduVideo> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id", id);
        baseMapper.delete(wrapper);
    }

    @Override
    public void removeVideoById(String id) {
        // 根据小节id获取视频id
        EduVideo eduVideo = baseMapper.selectById(id);
        String videoSourceId = eduVideo.getVideoSourceId();
        // 删除上传到阿里云的视频
        // 判断小节是否有视频id
        if (!StringUtils.isEmpty(videoSourceId)) {
            Result result = vodClient.removeAliyunVideo(videoSourceId);
            if (result.getCode() == 20001){
                throw new CoderException(20001, "删除视频失败");
            }
        }
        // 删除数据库中的记录
        QueryWrapper<EduVideo> wrapper = new QueryWrapper<>();
        wrapper.eq("id", id);
        baseMapper.delete(wrapper);
    }
}
