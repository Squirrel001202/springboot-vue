package com.example.service;

import com.example.model.Menu;
import com.example.model.Role;

import java.util.List;

public interface MenuService {
    List<Menu> findAll(String name);

    int saveOrUpdate(Menu menu);

    int removeById(Integer id);

    int removeByIds(List<Integer> ids);
}
