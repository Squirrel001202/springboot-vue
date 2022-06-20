package com.example.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.controller.dto.UserDTO;
import com.example.controller.dto.UserPasswordDTO;
import com.example.mapper.UserMapper;
import com.example.model.User;

import java.util.List;
import java.util.Map;

public interface UserService {
    List<User> selectAll();

    int save(User user);

    int deleteById(Integer id);

    Map<String,Object> selectPage(Integer pageNum, Integer pageSize,String username,String email,String address);

    int selectTotal(String username,String email,String address);

    int deleteBatch(List<Integer> id);

    int insertBatch(List<User> userList);

    UserDTO login(UserDTO userDTO);

    UserDTO register(UserDTO userDTO);

    User getPerson(String username);

    User getById(String id);

    void updatePassword(UserPasswordDTO userPasswordDTO);

    List<User> selectByRole(String role);
}
