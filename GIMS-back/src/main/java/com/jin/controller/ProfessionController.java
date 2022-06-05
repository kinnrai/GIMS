package com.jin.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jin.common.Result;
import com.jin.entity.Profession;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RequestMapping("/profession")
@RestController
public class ProfessionController extends BaseController{

    @RequestMapping("/list")
    public Result getList(String professionName) {
        Page<Profession> pageData = professionService.page(getPage(),
                new QueryWrapper<Profession>()
                        .like(StrUtil.isNotBlank(professionName), "profession_name", professionName)
        );

        return Result.success(pageData);
    }

    @GetMapping("/info/{id}")
    public Result getInfo(@PathVariable("id") Long id) {
        Profession profession = professionService.getById(id);
        return Result.success(profession);
    }

    @RequestMapping("/add")
    public Result addProfession(@RequestBody Profession profession) {
        professionService.save(profession);
        return Result.success(profession);
    }

    @RequestMapping("/update")
    public Result updateProfession(@RequestBody Profession profession) {
        professionService.updateById(profession);
        return Result.success(profession);
    }

    @RequestMapping("/delete")
    public Result deleteProfession(@RequestBody Long[] ids) {
        professionService.removeByIds(Arrays.asList(ids));
        return Result.success("");
    }

    @RequestMapping("/getAllProfession")
    public Result getAllProfession() {
        List<Profession> professionList = professionService.list();
        return Result.success(professionList);
    }
}
