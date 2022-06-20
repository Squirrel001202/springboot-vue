package com.example.controller;

import com.example.common.Constants;
import com.example.common.Result;
import com.example.mapper.DictMapper;
import com.example.model.Menu;
import com.example.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/menu")
public class MenuController {

    @Autowired
    private MenuService menuService;

    @Resource
    private DictMapper dictMapper;
//    @Resource
//    private IMenuService menuService;
//
//    @Resource
//    private DictMapper dictMapper;
//
//    // 新增或者更新
    @PostMapping
    public Result save(@RequestBody Menu menu) {
        if(menuService.saveOrUpdate(menu) != 0){
            return Result.success();
        }
        return Result.error(Constants.CODE_400,"名称不能为空");
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        menuService.removeById(id);
        return Result.success();
    }

    @PostMapping("/del/batch")
    public Result deleteBatch(@RequestBody List<Integer> ids) {
        menuService.removeByIds(ids);
        return Result.success();
    }

//    @GetMapping("/ids")
//    public Result findAllIds() {
//        return Result.success(menuService.list().stream().map(Menu::getId));
//    }

    @GetMapping
    public Result findAll(@RequestParam(defaultValue = "") String name) {
        return Result.success(menuService.findAll(name));
    }
//
//    @GetMapping("/{id}")
//    public Result findOne(@PathVariable Integer id) {
//        return Result.success(menuService.getById(id));
//    }
//
//    @GetMapping("/page")
//    public Result findPage(@RequestParam String name,
//                           @RequestParam Integer pageNum,
//                           @RequestParam Integer pageSize) {
//        QueryWrapper<Menu> queryWrapper = new QueryWrapper<>();
//        queryWrapper.like("name", name);
//        queryWrapper.orderByDesc("id");
//        return Result.success(menuService.page(new Page<>(pageNum, pageSize), queryWrapper));
//    }

    @GetMapping("/icons")
    public Result getIcons() {
        return Result.success(dictMapper.findAll());
    }

}
