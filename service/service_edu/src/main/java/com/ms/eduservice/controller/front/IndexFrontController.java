package com.ms.eduservice.controller.front;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ms.commonutils.R;
import com.ms.eduservice.entity.EduCourse;
import com.ms.eduservice.entity.EduTeacher;
import com.ms.eduservice.service.EduCourseService;
import com.ms.eduservice.service.EduTeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author MS
 * @create 2022-07-25-22:10
 */
@RestController
@RequestMapping("/eduservice/indexfront")
public class IndexFrontController {
    @Autowired
    private EduTeacherService teacherService;
    @Autowired
    private EduCourseService courseService;

    // 查询前8个热门课程，和前4个讲师
    @GetMapping("index")
    public R index(){
        QueryWrapper<EduTeacher> teacherQueryWrapper = new QueryWrapper<>();
        QueryWrapper<EduCourse> courseQueryWrapper = new QueryWrapper<>();
        teacherQueryWrapper.orderByDesc("id");
        courseQueryWrapper.orderByDesc("id");
        teacherQueryWrapper.last("limit 4");
        courseQueryWrapper.last("limit 8");
        List<EduTeacher> teachers = teacherService.list(teacherQueryWrapper);
        List<EduCourse> courses = courseService.list(courseQueryWrapper);
        return R.ok().data("teachers",teachers).data("courses",courses);
    }

}
