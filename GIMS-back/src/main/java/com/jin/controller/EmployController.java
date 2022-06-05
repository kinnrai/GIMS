package com.jin.controller;


import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jin.common.Result;
import com.jin.dto.EmployDetailDto;
import com.jin.entity.Employ;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RequestMapping("/employ")
@RestController
public class EmployController extends BaseController {


    @RequestMapping("/list")
    public Result getList(String employName, Long companyId) {
        Page<Employ> pageData = employService.page(getPage(),
                new QueryWrapper<Employ>()
                        .like(StrUtil.isNotBlank(employName), "employ_name", employName)
                        .eq(companyId != null, "company_id", companyId)
        );

        return Result.success(pageData);
    }

    @GetMapping("/info/{id}")
    public Result getInfo(@PathVariable("id") Long id) {
        Employ employ = employService.getById(id);
        return Result.success(employ);
    }

    @RequestMapping("/add")
    public Result addEmploy(@RequestBody Employ employ) {
        employService.save(employ);
        return Result.success(employ);
    }

    @RequestMapping("/update")
    public Result updateEmploy(@RequestBody Employ employ) {
        employService.updateById(employ);
        return Result.success(employ);
    }

    @RequestMapping("/delete")
    public Result deleteEmploy(@RequestBody Long[] ids) {
        employService.removeByIds(Arrays.asList(ids));
        return Result.success("");
    }

    @RequestMapping("/getAllEmploy")
    public Result getAllEmploy() {
        List<Employ> employList = employService.list();
        return Result.success(employList);
    }

    @RequestMapping("/detail")
    public Result getDetailedInfo(String employName) {
        List<EmployDetailDto> detailInfo = employService.getDetailedInfo(employName);
        return Result.success(detailInfo);
    }

}
