package com.example.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.example.mapper.MenuMapper;
import com.example.mapper.RoleMapper;
import com.example.mapper.RoleMenuMapper;
import com.example.model.Menu;
import com.example.model.Role;
import com.example.model.RoleMenu;
import com.example.model.User;
import com.example.service.MenuService;
import com.example.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Service
public class RoleServiceImpl implements RoleService {

    @Resource
    private RoleMapper roleMapper;

    @Resource
    private RoleMenuMapper roleMenuMapper;

    @Resource
    private MenuMapper menuMapper;

    @Transactional
    @Override
    public void setRoleMenu(Integer roleId, List<Integer> menuIds) {
        //先删除当前角色id所有的绑定关系
        roleMenuMapper.deleteByRoleId(roleId);
        //再把前端传过来的菜单id数组绑定到当前的角色id上去
        List<Integer> menuIdsCopy = CollUtil.newArrayList(menuIds);
        for(Integer menuId : menuIds){
            Menu menu = menuMapper.getById(menuId);
            //是二级菜单并且传过来的menuId数组里面没有他的父级Id
            if(menu.getPid() != null && !menuIdsCopy.contains(menu.getPid())){
                //补上这个父级Id
                RoleMenu roleMenu = new RoleMenu();
                roleMenu.setRoleId(roleId);
                roleMenu.setMenuId(menu.getPid());
                roleMenuMapper.insert(roleMenu);
                menuIdsCopy.add(menu.getPid());
            }
            RoleMenu roleMenu = new RoleMenu();
            roleMenu.setRoleId(roleId);
            roleMenu.setMenuId(menuId);
            roleMenuMapper.insert(roleMenu);
        }
    }

    @Override
    public List<Integer> getRoleMenu(Integer roleId) {
        return roleMenuMapper.getByRoleId(roleId);
    }

    @Override
    public Map<String, Object> findPage(String name, Integer pageNum, Integer pageSize) {
        pageNum = (pageNum - 1) * pageSize;
        List<Role> data = roleMapper.findPage(name,pageNum, pageSize);
        Integer total = roleMapper.findTotal(name);
        Map<String, Object> roleMap = new HashMap<>();
        roleMap.put("data", data);
        roleMap.put("total", total);
        return roleMap;
    }

    @Override
    public Integer saveOrUpdate(Role role) {
        if (role.getId() == null) { //role没有id，表示新增，否则更新
            //名字，描述，唯一标识都不为空或空串
            if(null != role.getName() && !("".equals(role.getName()))
            && null != role.getDescription() && !("".equals(role.getDescription()))
            && null != role.getFlag() && !("".equals(role.getFlag()))){
                return roleMapper.insert(role);
            }else{
                return 0;
            }
        } else {
            if(null != role.getName() && !("".equals(role.getName()))
                    && null != role.getDescription() && !("".equals(role.getDescription()))
                    && null != role.getFlag() && !("".equals(role.getFlag()))){
                return roleMapper.update(role);
            }else{
                return 0;
            }
        }
    }

    @Override
    public int removeById(Integer id) {
        return roleMapper.removeById(id);
    }

    @Override
    public int removeByIds(List<Integer> ids) {
        return roleMapper.removeByIds(ids);
    }

    @Override
    public List<Role> findAll() {
        return roleMapper.findAll();
    }
}
