package com.example.service.impl;

import cn.hutool.core.lang.TypeReference;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.example.common.Constants;
import com.example.controller.dto.UserDTO;
import com.example.controller.dto.UserPasswordDTO;
import com.example.exception.ServiceException;
import com.example.mapper.MenuMapper;
import com.example.mapper.RoleMapper;
import com.example.mapper.RoleMenuMapper;
import com.example.mapper.UserMapper;
import com.example.model.Menu;
import com.example.model.User;
import com.example.service.UserService;
import com.example.utils.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;

    @Resource
    private RoleMapper roleMapper;

    @Resource
    private RoleMenuMapper roleMenuMapper;

    @Resource
    private MenuMapper menuMapper;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public List<User> selectAll() {
        return userMapper.selectAll();
    }

    @Override
    public int save(User user) {
        if (user.getId() == null) { //user没有id，表示新增，否则更新
            if(null != user.getUsername() && !("".equals(user.getUsername()))){
                flushRedis();
                return userMapper.insert(user);
            }else{
                return 0;
            }
        } else {
            if(null != user.getUsername() && !("".equals(user.getUsername()))){
                flushRedis();
                return userMapper.update(user);
            }
            return 0;
        }
    }

    @Override
    public int deleteById(Integer id) {
        flushRedis();
        return userMapper.deleteById(id);
    }

    @Override
    public Map<String, Object> selectPage(Integer pageNum, Integer pageSize, String username, String email, String address) {
        pageNum = (pageNum - 1) * pageSize;
        //String jsonStr = stringRedisTemplate.opsForValue().get(Constants.USER_ALL);
        Map<String, Object> userMap = new HashMap<>();
        //redis中没有数据，查询数据库，并保存到redis
        //if(StrUtil.isBlank(jsonStr)){
            List<User> data = userMapper.selectPage(pageNum, pageSize, username, email, address);
            Integer total = userMapper.selectTotal(username, email, address);
            userMap = new HashMap<>();
            userMap.put("data", data);
            userMap.put("total", total);
            //stringRedisTemplate.opsForValue().set(Constants.USER_ALL, JSONUtil.toJsonStr(userMap));
            return userMap;
        //}else{
            //redis中有数据，直接获取
            //userMap = JSONUtil.toBean(jsonStr, new TypeReference<Map<String, Object>>() {
            //},true);
            //return userMap;
        //}
    }

    @Override
    public int selectTotal(String username, String email, String address) {
        return userMapper.selectTotal(username, email, address);
    }

    @Override
    public int deleteBatch(List<Integer> id) {
        flushRedis();
        return userMapper.deleteBatch(id);
    }

    @Override
    public int insertBatch(List<User> userList) {
        return userMapper.insertBatch(userList);
    }

    public UserDTO login(UserDTO userDTO) {
        UserDTO userDTOResult;
        try{
            userDTOResult = userMapper.login(userDTO);
        }catch (Exception e){
            throw new ServiceException(Constants.CODE_500,"系统错误");
        }
        if(userDTOResult != null){
            //设置token
            String token = TokenUtil.genToken(userDTOResult.getId(), userDTOResult.getPassword());
            userDTOResult.setToken(token);
            String role = userDTOResult.getRole();
            Integer roleId = roleMapper.getByFlag(role);
            List<Integer> menuIds = roleMenuMapper.getByRoleId(roleId);

            //查出所有用户的菜单
            List<Menu> list = menuMapper.findAll("");
            // 找出pid为null的一级菜单
            List<Menu> menus = list.stream().filter(menu -> menu.getPid() == null).collect(Collectors.toList());
            // 找出一级菜单的子菜单
            for (Menu menu : menus) {
                // 筛选所有数据中pid=父级id的数据就是二级菜单
                menu.setChildren(list.stream().filter(m -> menu.getId().equals(m.getPid())).collect(Collectors.toList()));
            }

            List<Menu> roleMenus = new ArrayList<>();
            //筛选当前用户的菜单
            for (Menu menu : menus) {
                if(menuIds.contains(menu.getId())){
                    roleMenus.add(menu);
                }
                List<Menu> children = menu.getChildren();
                //移除children里面不在menuIds集合中的元素
                children.removeIf(child -> !menuIds.contains(child.getId()));
            }
            userDTOResult.setMenus(roleMenus);
            return userDTOResult;
        }else{
            throw new ServiceException(Constants.CODE_600,"用户名或密码错误");
        }
    }

    public UserDTO register(UserDTO userDTO) {
        UserDTO userDTOResult = userMapper.register(userDTO);
        if(userDTOResult == null){
            User user = new User();
            user.setUsername(userDTO.getUsername());
            user.setPassword(userDTO.getPassword());
            user.setNickname(userDTO.getNickname());
            userMapper.insert(user);
        }else{
            throw new ServiceException(Constants.CODE_600,"用户已存在");
        }
        return userDTOResult;
    }

    @Override
    public User getPerson(String username) {
        return userMapper.getPerson(username);
    }

    @Override
    public User getById(String id) {
        return userMapper.getById(id);
    }

    @Override
    public void updatePassword(UserPasswordDTO userPasswordDTO) {
        int update = userMapper.updatePassword(userPasswordDTO);
        if (update < 1) {
            throw new ServiceException(Constants.CODE_600, "密码错误");
        }
    }

    @Override
    public List<User> selectByRole(String role) {
        return userMapper.selectByRole(role);
    }

    private void flushRedis(){
        stringRedisTemplate.delete(Constants.USER_ALL);
    }
}
