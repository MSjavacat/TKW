package com.ms.eduservice.service;

import com.ms.eduservice.entity.EduVideo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 课程视频 服务类
 * </p>
 *
 * @author testjava
 * @since 2022-07-20
 */
public interface EduVideoService extends IService<EduVideo> {

    void removeByCourseId(String id);
}
