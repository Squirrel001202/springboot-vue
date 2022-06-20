package com.example.service.impl;

import com.example.mapper.MenuMapper;
import com.example.model.Menu;
import com.example.model.Role;
import com.example.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MenuServiceImpl implements MenuService {

    @Resource
    private MenuMapper menuMapper;

    @Override
    public List<Menu> findAll(String name) {
        List<Menu> list = menuMapper.findAll(name);
        // 找出pid为null的一级菜单
        List<Menu> parentNodes = list.stream().filter(menu -> menu.getPid() == null).collect(Collectors.toList());
        // 找出一级菜单的子菜单
        for (Menu menu : parentNodes) {
            // 筛选所有数据中pid=父级id的数据就是二级菜单
            menu.setChildren(list.stream().filter(m -> menu.getId().equals(m.getPid())).collect(Collectors.toList()));
        }
        return parentNodes;
    }

    @Override
    public int saveOrUpdate(Menu menu) {
        if (menu.getId() == null) { //role没有id，表示新增，否则更新
            //名字，描述，唯一标识都不为空或空串
            if(null != menu.getName() && !("".equals(menu.getName()))){
                return menuMapper.insert(menu);
            }else{
                return 0;
            }
        } else {
            if(null != menu.getName() && !("".equals(menu.getName()))){
                return menuMapper.update(menu);
            }else{
                return 0;
            }
        }
    }
    @Override
    public int removeById(Integer id) {
        return menuMapper.removeById(id);
    }

    @Override
    public int removeByIds(List<Integer> ids) {
        return menuMapper.removeByIds(ids);
    }
}
