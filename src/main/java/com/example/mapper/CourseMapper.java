package com.example.mapper;

import com.example.model.Course;

import java.util.List;
import java.util.Map;

public interface CourseMapper {
    List<Course> findPage(String name, Integer pageNum, Integer pageSize);

    Integer findTotal(String name);

    Integer removeById(Integer id);

    Integer removeByIds(List<Integer> id);

    List<Course> findAll();

    Integer insert(Course course);

    Integer update(Course course);

    String getTeacherById(Integer id);

    int setStudentCourse(Integer courseId, Integer studentId);

    int deleteStudentCourse(Integer courseId, Integer studentId);
}
