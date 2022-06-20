package com.example.service;

import com.example.model.Role;

import java.util.List;
import java.util.Map;

public interface RoleService {

    void setRoleMenu(Integer roleId, List<Integer> menuIds);

    List<Integer> getRoleMenu(Integer roleId);

    Map<String,Object> findPage(String name, Integer pageNum, Integer pageSize);

    Integer saveOrUpdate(Role role);

    int removeById(Integer id);

    int removeByIds(List<Integer> ids);

    List<Role> findAll();

}
