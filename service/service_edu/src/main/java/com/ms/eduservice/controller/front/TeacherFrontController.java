package com.ms.eduservice.controller.front;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ms.commonutils.R;
import com.ms.eduservice.entity.EduCourse;
import com.ms.eduservice.entity.EduTeacher;
import com.ms.eduservice.service.EduCourseService;
import com.ms.eduservice.service.EduTeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author MS
 * @create 2022-07-28-16:16
 */
@RestController
@RequestMapping("/eduservice/teacherfront")
public class TeacherFrontController {

    @Autowired
    private EduTeacherService teacherService;

    @Autowired
    private EduCourseService courseService;

    // 分页查询讲师
    @GetMapping("getTeacherFrontList/{current}/{limit}")
    public R getTeacherFrontList(@PathVariable long current, @PathVariable long limit){
        Page<EduTeacher> teacherPage = new Page<>(current,limit);
        Map<String, Object> map = teacherService.getTeacherFrontList(teacherPage);
        return R.ok().data(map);
    }

    // 根据id查询讲师详情
    @GetMapping("getTeacherFrontInfo/{teacherId}")
    public R getTeacherFrontInfo(@PathVariable String teacherId){
        // 根据id查询讲师基本信息
        EduTeacher eduTeacher = teacherService.getById(teacherId);

        // 根据id查询讲师主将课程
        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();
        wrapper.eq("teacher_id",teacherId);
        List<EduCourse> courses = courseService.list(wrapper);

        return R.ok().data("teacher",eduTeacher).data("courseList",courses);
    }

}
