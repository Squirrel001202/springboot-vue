package com.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.controller.dto.UserDTO;
import com.example.controller.dto.UserPasswordDTO;
import com.example.model.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

//@Mapper
public interface UserMapper {
    List<User> selectAll();

    int insert(User user);

    int update(User user);

    int deleteById(Integer id);

    List<User> selectPage(Integer pageNum,Integer pageSize,String username,String email,String address);

    int selectTotal(String username,String email,String address);

    int deleteBatch(List<Integer> id);

    int insertBatch(List<User> userList);

    UserDTO login(UserDTO userDTO);

    UserDTO register(UserDTO userDTO);

    User getPerson(String username);

    User getById(String id);

    int updatePassword(UserPasswordDTO userPasswordDTO);

    List<User> selectByRole(String role);
}
