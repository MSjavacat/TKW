package com.ms.eduservice.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ms.eduservice.entity.EduCourse;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ms.eduservice.entity.frontvo.CourseFrontVo;
import com.ms.eduservice.entity.frontvo.CourseWebVo;
import com.ms.eduservice.entity.vo.CourseInfoVo;
import com.ms.eduservice.entity.vo.CoursePublishVo;

import java.util.Map;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author testjava
 * @since 2022-07-20
 */
public interface EduCourseService extends IService<EduCourse> {

    String saveCourseInfo(CourseInfoVo courseInfoVo);

    CourseInfoVo getCourseInfo(String courseId);

    void updateCourseInfo(CourseInfoVo courseInfoVo);

    CoursePublishVo publishCourseInfo(String id);

    void removeCourse(String id);

    Map<String, Object> getCourseFrontList(Page<EduCourse> coursePage, CourseFrontVo courseFrontVo);

    CourseWebVo getBaseCourseInfo(String courseId);
}
