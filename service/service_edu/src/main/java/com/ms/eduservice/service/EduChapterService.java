package com.ms.eduservice.service;

import com.ms.eduservice.entity.EduChapter;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ms.eduservice.entity.chapter.ChapterVo;
import com.ms.eduservice.entity.vo.CourseInfoVo;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author testjava
 * @since 2022-07-20
 */
public interface EduChapterService extends IService<EduChapter> {

    List<ChapterVo> getChapterVideoByCourseId(String courseId);

    boolean deleteChapter(String chapterId);

    void removeByCourseId(String id);
}
