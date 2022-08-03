package com.ms.eduservice.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ms.commonutils.R;
import com.ms.eduservice.entity.EduCourse;
import com.ms.eduservice.entity.vo.CourseInfoVo;
import com.ms.eduservice.entity.vo.CoursePublishVo;
import com.ms.eduservice.entity.vo.CourseQuery;
import com.ms.eduservice.service.EduCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2022-07-20
 */
@RestController
@RequestMapping("/eduservice/course")
public class EduCourseController {

    @Autowired
    private EduCourseService courseService;

    // 删除课程（删除小节，章节，描述信息，最后删除课程）
    @DeleteMapping("{id}")
    public R removeCourse(@PathVariable String id){
        courseService.removeCourse(id);
        return R.ok();
    }

    // 分页查询课程
    @GetMapping("getCourseListPage/{current}/{limit}")
    public R getCourseListCondition(@PathVariable long current,
                                    @PathVariable long limit){

        Page<EduCourse> coursePage = new Page<>(current,limit);

        // 调用分页查询，封装到coursepage中
        courseService.page(coursePage,null);

        // 获取总记录数，和数量
        List<EduCourse> records = coursePage.getRecords();
        long total = coursePage.getTotal();

        Map<String, Object> map = new HashMap<>();
        map.put("records",records);
        map.put("total",total);

        return R.ok().data(map);
    }

    //条件查询课程
    @PostMapping("getCourseListCondition/{current}/{limit}")
    public R getCourseListCondition(@PathVariable long current,
                                    @PathVariable long limit,
                                    @RequestBody(required = false) CourseQuery courseQuery){

        Page<EduCourse> eduCoursePage = new Page<>(current,limit);

        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();

        String title = courseQuery.getTitle();
        String status = courseQuery.getStatus();

        if (!StringUtils.isEmpty(title)) {
            wrapper.like("title",title);
        }
        if (!StringUtils.isEmpty(status)) {
            wrapper.eq("status",status);
        }

        courseService.page(eduCoursePage,wrapper);

        long total = eduCoursePage.getTotal();

        List<EduCourse> records = eduCoursePage.getRecords();

        Map<String, Object> map = new HashMap<>();

        map.put("total",total);
        map.put("rows",records);

        return R.ok().data(map);
    }

//=========================================================================

    @PostMapping("addCourseInfo")
    public R addCourseInfo(@RequestBody CourseInfoVo courseInfoVo){
        // 返回添加课程的id，供下一步使用
        String id = courseService.saveCourseInfo(courseInfoVo);
        return R.ok().data("courseId",id);
    }

    // 根据课程id查询课程基本信息
    @GetMapping("/getCourseInfo/{courseId}")
    public R getCourseInfo(@PathVariable String courseId){
        CourseInfoVo courseInfoVo = courseService.getCourseInfo(courseId);
        return R.ok().data("courseInfoVo",courseInfoVo);
    }

    // 修改课程信息
    @PostMapping("/updateCourseInfo")
    public R updateCourseInfo(@RequestBody CourseInfoVo courseInfoVo){
        courseService.updateCourseInfo(courseInfoVo);
        return R.ok();
    }

    // 获取课程发布确认信息根据id
    @GetMapping("getPublishCourseInfo/{id}")
    public R getPublishCourseInfo(@PathVariable String id){
        CoursePublishVo coursePublishVo = courseService.publishCourseInfo(id);
        return R.ok().data("coursePublishVo",coursePublishVo);
    }

    // 课程最终发布（修改课程状态）
    @PostMapping("publishCourse/{id}")
    public R publishCourse(@PathVariable String id){
        EduCourse eduCourse = new EduCourse();
        eduCourse.setId(id);
        eduCourse.setStatus("Normal");
        courseService.updateById(eduCourse);
        return R.ok();
    }
}

/*
    mvn install:install-file -Dfile=aliyun-java-vod-upload-1.4.11.jar -DgroupId=com.aliyun -DartifactId=aliyun-sdk-vod-upload -Dversion=1.4.11 -Dpackaging=jar

 */
