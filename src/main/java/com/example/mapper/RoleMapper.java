package com.example.mapper;

import com.example.model.Role;
import com.example.model.User;

import java.util.List;

public interface RoleMapper {
    List<Role> findPage(String name, Integer pageNum, Integer pageSize);

    Integer findTotal(String name);

    int insert(Role role);

    int update(Role role);

    int removeById(Integer id);

    int removeByIds(List<Integer> ids);

    List<Role> findAll();

    Integer getByFlag(String flag);
}
