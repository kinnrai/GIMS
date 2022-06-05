package com.jin.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jin.common.Result;
import com.jin.entity.School;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * @author 金磊
 */
@RequestMapping("/school")
@RestController
public class SchoolController extends BaseController{

    @RequestMapping("/list")
    public Result getList(String schoolName) {
        Page<School> pageData = schoolService.page(getPage(),
                new QueryWrapper<School>()
                        .like(StrUtil.isNotBlank(schoolName), "school_name", schoolName)
        );

        return Result.success(pageData);
    }

    @GetMapping("/info/{id}")
    public Result getInfo(@PathVariable("id") Long id) {
        School school = schoolService.getById(id);
        return Result.success(school);
    }

    @RequestMapping("/add")
    public Result addSchool(@RequestBody School school) {
        schoolService.save(school);
        return Result.success(school);
    }

    @RequestMapping("/update")
    public Result updateSchool(@RequestBody School school) {
        schoolService.updateById(school);
        return Result.success(school);
    }

    @RequestMapping("/delete")
    public Result deleteSchool(@RequestBody Long[] ids) {
        schoolService.removeByIds(Arrays.asList(ids));
        return Result.success("");
    }

    @RequestMapping("/getAllSchool")
    public Result getAllSchool(){
        List<School> schoolList = schoolService.list();
        return Result.success(schoolList);
    }
}
