package com.jin.controller;


import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jin.common.Result;
import com.jin.entity.Major;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RequestMapping("/major")
@RestController
public class MajorController extends BaseController{

    @RequestMapping("/list")
    public Result getList(String majorName) {
        Page<Major> pageData = majorService.page(getPage(),
                new QueryWrapper<Major>()
                        .like(StrUtil.isNotBlank(majorName), "major_name", majorName)
        );

        return Result.success(pageData);
    }

    @GetMapping("/info/{id}")
    public Result getInfo(@PathVariable("id") Long id) {
        Major major = majorService.getById(id);
        return Result.success(major);
    }

    @RequestMapping("/add")
    public Result addMajor(@RequestBody Major major) {
        majorService.save(major);
        return Result.success(major);
    }

    @RequestMapping("/update")
    public Result updateMajor(@RequestBody Major major) {
        majorService.updateById(major);
        return Result.success(major);
    }

    @RequestMapping("/delete")
    public Result deleteMajor(@RequestBody Long[] ids) {
        majorService.removeByIds(Arrays.asList(ids));
        return Result.success("");
    }

    @RequestMapping("/getAllMajor")
    public Result getAllMajor(){
        List<Major> majorList = majorService.list();
        return Result.success(majorList);
    }
}
