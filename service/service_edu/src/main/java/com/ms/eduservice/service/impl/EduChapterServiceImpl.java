package com.ms.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ms.eduservice.entity.EduChapter;
import com.ms.eduservice.entity.EduVideo;
import com.ms.eduservice.entity.chapter.ChapterVo;
import com.ms.eduservice.entity.chapter.VideoVo;
import com.ms.eduservice.mapper.EduChapterMapper;
import com.ms.eduservice.service.EduChapterService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ms.eduservice.service.EduVideoService;
import com.ms.servicebase.exceptionhandler.EduException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2022-07-20
 */
@Service
public class EduChapterServiceImpl extends ServiceImpl<EduChapterMapper, EduChapter> implements EduChapterService {
    @Autowired
    private EduVideoService videoService;

    @Override
    public List<ChapterVo> getChapterVideoByCourseId(String courseId) {
        // 根据课程id查询所有章节
        QueryWrapper<EduChapter> chapterQueryWrapper = new QueryWrapper<>();
        chapterQueryWrapper.eq("course_id",courseId);
        List<EduChapter> eduChapters = baseMapper.selectList(chapterQueryWrapper);

        // 根据课程id查询所有小节
        QueryWrapper<EduVideo> videoQueryWrapper = new QueryWrapper<>();
        videoQueryWrapper.eq("course_id",courseId);
        List<EduVideo> eduVideos = videoService.list(videoQueryWrapper);

        // 返回结果
        List<ChapterVo> finalChapterList = new ArrayList<>();

        Map<String, ChapterVo> map = new HashMap<>();

        for (EduChapter eduChapter : eduChapters){
            ChapterVo chapterVo = new ChapterVo();
            BeanUtils.copyProperties(eduChapter,chapterVo);
            finalChapterList.add(chapterVo);
            map.put(chapterVo.getId(),chapterVo);
        }

        for (EduVideo eduVideo : eduVideos){
            ChapterVo chapterVo = map.get(eduVideo.getChapterId());
            VideoVo videoVo = new VideoVo();
            BeanUtils.copyProperties(eduVideo,videoVo);
            chapterVo.getChildren().add(videoVo);
        }

        return finalChapterList;
    }

    @Override
    public boolean deleteChapter(String chapterId) {
        QueryWrapper<EduVideo> wrapper = new QueryWrapper<>();
        wrapper.eq("chapter_id",chapterId);
        int count = videoService.count(wrapper);
        if (count == 0) {
            // 可以进行删除
            int result = baseMapper.deleteById(chapterId);
            return result > 0;
        }else {
            // 不能删除
            throw new EduException(20001,"该章节还有小节，不能删除");
        }
    }

    @Override
    public void removeByCourseId(String id) {
        QueryWrapper<EduChapter> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id",id);
        baseMapper.delete(wrapper);
    }
}
