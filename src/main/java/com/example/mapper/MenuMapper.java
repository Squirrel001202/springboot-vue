package com.example.mapper;

import com.example.model.Menu;
import com.example.model.Role;

import java.util.List;
import java.util.MissingResourceException;

public interface MenuMapper {
    List<Menu> findAll(String name);

    int insert(Menu menu);

    int update(Menu menu);

    int removeById(Integer id);

    int removeByIds(List<Integer> ids);

    Menu getById(Integer id);
}
