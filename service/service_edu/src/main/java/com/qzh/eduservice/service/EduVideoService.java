package com.qzh.eduservice.service;

import com.qzh.eduservice.entity.EduVideo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 课程视频 服务类
 * </p>
 *
 * @author qzh
 * @since 2021-04-14
 */
public interface EduVideoService extends IService<EduVideo> {

    /**
     * 根据课程id删除小节并且删除阿里云中的视频
     * @param id
     */
    void removeVideoByCourseId(String id);

    /**
     * 删除小节并且删除阿里云中的视频
     * @param id
     */
    void removeVideoById(String id);
}
