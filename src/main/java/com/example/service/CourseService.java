package com.example.service;

import com.example.model.Course;

import java.util.List;
import java.util.Map;

public interface CourseService {
    Map<String, Object> findPage(String name, Integer pageNum, Integer pageSize);

    Integer removeById(Integer id);

    Integer removeByIds(List<Integer> id);

    List<Course> findAll();

    Integer saveOrUpdate(Course course);

    String getTeacherById(Integer id);

    int setStudentCourse(Integer courseId, Integer studentId);

    Integer update(Course course);
}
