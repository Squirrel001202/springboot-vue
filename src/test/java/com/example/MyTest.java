package com.example;

import com.example.controller.dto.UserDTO;
import com.example.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;

public class MyTest {
    @Resource
    private UserMapper userMapper;
    @Test
    public void getUserDTO(){
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername("admin");
        userDTO.setPassword("admin");
        UserDTO userDTO1 = userMapper.login(userDTO);
        System.out.println(userDTO1);
    }
}
