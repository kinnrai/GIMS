package com.jin.controller;


import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jin.common.Result;
import com.jin.entity.Classs;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RequestMapping("/class")
@RestController
public class ClassController extends BaseController {
    @RequestMapping("/list")
    public Result getList(String className) {
        Page<Classs> pageData = classsService.page(getPage(),
                new QueryWrapper<Classs>()
                        .like(StrUtil.isNotBlank(className), "class_name", className)
        );

        return Result.success(pageData);
    }

    @GetMapping("/info/{id}")
    public Result getInfo(@PathVariable("id") Long id) {
        Classs classs = classsService.getById(id);
        return Result.success(classs);
    }

    @RequestMapping("/add")
    public Result addClasss(@RequestBody Classs classs) {
        classsService.save(classs);
        return Result.success(classs);
    }

    @RequestMapping("/update")
    public Result updateClasss(@RequestBody Classs classs) {
        classsService.updateById(classs);
        return Result.success(classs);
    }

    @RequestMapping("/delete")
    public Result deleteClasss(@RequestBody Long[] ids) {
        classsService.removeByIds(Arrays.asList(ids));
        return Result.success("");
    }

    @RequestMapping("/getAllClass")
    public Result getAllClasss() {
        List<Classs> classList = classsService.list();
        return Result.success(classList);
    }

    @RequestMapping("/ownClass")
    public Result getOwnClass(@RequestBody Long id) {
        List<Classs> classsList = classsService.getOwnClass(id);
        return Result.success(classsList);
    }
}
