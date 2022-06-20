package com.example.controller.dto;

import com.example.model.Menu;
import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * 接收前端登录请求的参数
 */
@Data
@ToString
public class UserDTO {

    private String id;

    private String username;

    private String password;

    private String nickname;

    private String token;

    private String role;

    private List<Menu> menus;
}
