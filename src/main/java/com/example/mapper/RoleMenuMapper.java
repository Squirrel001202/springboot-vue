package com.example.mapper;

import com.example.model.RoleMenu;

import java.util.List;

public interface RoleMenuMapper {
    int deleteByRoleId(Integer id);

    int insert(RoleMenu roleMenu);

    List<Integer> getByRoleId(Integer id);
}
