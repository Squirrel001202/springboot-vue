package com.example.controller;

import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.example.common.Constants;
import com.example.common.Result;
import com.example.controller.dto.UserDTO;
import com.example.controller.dto.UserPasswordDTO;
import com.example.exception.ServiceException;
import com.example.model.User;
import com.example.service.impl.UserServiceImpl;
import com.example.utils.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserServiceImpl userService;

    @GetMapping("/selectAll")
    public List<User> selectAll(){
        //查询全部
        return userService.selectAll();
    }

    @PostMapping("/save")
    public Result save(@RequestBody User user){
        //新增或更新
        if(userService.save(user) != 0){
            return Result.success();
        }else {
            return Result.error(Constants.CODE_400,"名字不能为空");
        }
    }
    @DeleteMapping("/{id}")
    public Result deleteById(@PathVariable Integer id){
        //删除
        return Result.success(userService.deleteById(id));
    }
    //@RequestParam 接收？page=2&pageSize=10
    @GetMapping("/page")
    public Map<String,Object> selectPage(@RequestParam Integer pageNum,
                                         @RequestParam Integer pageSize,
                                         @RequestParam String username,
                                         @RequestParam String email,
                                         @RequestParam String address){
        //动态条件查询全部
        return userService.selectPage(pageNum,pageSize,username,email,address);
    }

    @PostMapping("/del/batch")
    public Result deleteBatch(@RequestBody List<Integer> id){
        return Result.success(userService.deleteBatch(id));
    }

    /**
     * 导出接口
     */
    @GetMapping("/export")
    public void export(HttpServletResponse response) throws Exception{
        //从数据库查询出所有的用户
        List<User> list = userService.selectAll();
        //在内存操作，写出到浏览器
        ExcelWriter writer = ExcelUtil.getWriter(true);

        //一次性写出list内的对象到excel，使用默认样式，强制输出标题
        writer.write(list,true);
        //设置浏览器响应的格式
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        String fileName = URLEncoder.encode("用户信息","Utf-8");
        response.setHeader("Content-Disposition","attachment;filename=" + fileName + ".xlsx");

        ServletOutputStream out = response.getOutputStream();
        writer.flush(out,true);
        out.close();
        writer.close();
    }

    /**
     * excel 导入
     * @param file
     * @throws Exception
     */
    @PostMapping("/import")
    public void imp(MultipartFile file) throws Exception{
        InputStream inputStream = file.getInputStream();
        ExcelReader reader = ExcelUtil.getReader(inputStream);
        List<User> list = reader.readAll(User.class);
        userService.insertBatch(list);
    }

    @PostMapping("/login")
    public Result login(@RequestBody UserDTO userDTO){
        String username = userDTO.getUsername();
        String password = userDTO.getPassword();
        if(StrUtil.isBlank(username) || StrUtil.isBlank(password)){
            return Result.error(Constants.CODE_400,"参数错误");
        }
        UserDTO userDTOResult = userService.login(userDTO);
        return Result.success(userDTOResult);
    }

    @PostMapping("/register")
    public Result register(@RequestBody UserDTO userDTO) {
        String username = userDTO.getUsername();
        String password = userDTO.getPassword();
        if(StrUtil.isBlank(username) || StrUtil.isBlank(password)){
            return Result.error(Constants.CODE_400,"参数错误");
        }
        return Result.success(userService.register(userDTO));
    }
    @GetMapping("/username/{username}")
    public Result getPerson(@PathVariable String username){
        return Result.success(userService.getPerson(username));
    }

    @PostMapping("/password")
    public Result password(@RequestBody UserPasswordDTO userPasswordDTO) {
//        userPasswordDTO.setPassword(SecureUtil.md5(userPasswordDTO.getPassword()));
//        userPasswordDTO.setNewPassword(SecureUtil.md5(userPasswordDTO.getNewPassword()));
        userService.updatePassword(userPasswordDTO);
        return Result.success();
    }
    @GetMapping("/role/{role}")
    public Result findUsersByRole(@PathVariable String role) {
        List<User> userList = userService.selectByRole(role);
        return Result.success(userList);
    }
}
