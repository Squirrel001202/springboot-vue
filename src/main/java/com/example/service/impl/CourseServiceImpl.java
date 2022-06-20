package com.example.service.impl;

import com.example.mapper.CourseMapper;
import com.example.model.Course;
import com.example.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CourseServiceImpl implements CourseService {

    @Resource
    private CourseMapper courseMapper;

    @Override
    public Map<String, Object> findPage(String name, Integer pageNum, Integer pageSize) {
        pageNum = (pageNum - 1) * pageSize;
        List<Course> data = courseMapper.findPage(name,pageNum, pageSize);
        Integer total = courseMapper.findTotal(name);
        Map<String, Object> courseMap = new HashMap<>();
        courseMap.put("data", data);
        courseMap.put("total", total);
        return courseMap;
    }

    @Override
    public Integer removeById(Integer id) {
        return courseMapper.removeById(id);
    }

    @Override
    public Integer removeByIds(List<Integer> id) {
        return courseMapper.removeByIds(id);
    }

    @Override
    public List<Course> findAll() {
        return null;
    }

    @Override
    public Integer saveOrUpdate(Course course) {
        if (course.getId() == null) { //user没有id，表示新增，否则更新
            if(null != course.getName() && !("".equals(course.getName()))){
                return courseMapper.insert(course);
            }else{
                return 0;
            }
        } else {
            if(null != course.getName() && !("".equals(course.getName()))){
                return courseMapper.update(course);
            }
            return 0;
        }
    }

    @Override
    public String getTeacherById(Integer id) {
        return courseMapper.getTeacherById(id);
    }

    @Transactional
    @Override
    public int setStudentCourse(Integer courseId, Integer studentId) {
        courseMapper.deleteStudentCourse(courseId,studentId);
       return courseMapper.setStudentCourse(courseId,studentId);
    }

    @Override
    public Integer update(Course course) {
        return courseMapper.update(course);
    }
}
