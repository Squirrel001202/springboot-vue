package com.example.mapper;

import com.example.model.Files;

import java.util.List;

public interface FilesMapper {

    int insert(Files savaFile);

    Files getByMd5(String md5);

    List<Files> selectPage(Integer pageNum,Integer pageSize,String name);

    int selectTotal(String name);

    Files selectById(Integer id);

    int deleteById(Integer id);

    int deleteBatch(List<Integer> id);

    int updateById(Files files);

    List<Files> selectByIds(List<Integer> id);
}
