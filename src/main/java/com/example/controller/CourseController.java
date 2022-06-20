package com.example.controller;

import com.example.common.Constants;
import com.example.common.Result;
import com.example.model.Course;
import com.example.service.CourseService;
import com.example.service.UserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/course")
public class CourseController {

    @Resource
    private CourseService courseService;

    @Resource
    private UserService userService;

    // 新增或者更新
    @PostMapping
    public Result save(@RequestBody Course course) {
        if(courseService.saveOrUpdate(course) != 0){
            return Result.success();
        }else{
            return Result.error(Constants.CODE_400,"名称不能为空");
        }
    }
    @PostMapping("/studentCourse/{courseId}/{studentId}")
    public Result studentCourse(@PathVariable Integer courseId, @PathVariable Integer studentId) {
        courseService.setStudentCourse(courseId, studentId);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        courseService.removeById(id);
        return Result.success();
    }

    @PostMapping("/del/batch")
    public Result deleteBatch(@RequestBody List<Integer> id) {
        courseService.removeByIds(id);
        return Result.success();
    }

    @GetMapping
    public Result findAll() {
        return Result.success(courseService.findAll());
    }

//    @GetMapping("/{id}")
//    public Result findOne(@PathVariable Integer id) {
//        return Result.success(courseService.getById(id));
//    }

    @GetMapping("/page")
    public Result findPage(@RequestParam String name,
                           @RequestParam Integer pageNum,
                           @RequestParam Integer pageSize) {
        return Result.success(courseService.findPage(name,pageNum,pageSize));
    }
    @PostMapping("/update")
    public Result update(@RequestBody Course course) {
        return Result.success(courseService.update(course));
    }
}
