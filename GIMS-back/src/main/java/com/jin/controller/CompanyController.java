package com.jin.controller;


import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jin.common.Result;
import com.jin.entity.Company;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;


/**
 * @author 金磊
 */
@RequestMapping("/company")
@RestController
public class CompanyController extends BaseController {

    @RequestMapping("/list")
    public Result getList(String companyName) {
        Page<Company> pageData = companyService.page(getPage(),
                new QueryWrapper<Company>()
                        .like(StrUtil.isNotBlank(companyName), "company_name", companyName)
        );

        return Result.success(pageData);
    }

    @GetMapping("/info/{id}")
    public Result getInfo(@PathVariable("id") Long id) {
        Company company = companyService.getById(id);
        return Result.success(company);
    }

    @RequestMapping("/add")
    public Result addSchool(@RequestBody Company company) {
        companyService.save(company);
        return Result.success(company);
    }

    @RequestMapping("/update")
    public Result updateSchool(@RequestBody Company company) {
        companyService.updateById(company);
        return Result.success(company);
    }

    @RequestMapping("/delete")
    public Result deleteSchool(@RequestBody Long[] ids) {
        companyService.removeByIds(Arrays.asList(ids));
        return Result.success("");
    }

    @RequestMapping("/getAllCompany")
    public Result getAllCompany() {
        List<Company> companyList = companyService.list();
        return Result.success(companyList);
    }

    @RequestMapping("/getBelongCompany")
    public Result getBelongCompany(@RequestBody Long accountId) {
        Long companyId = companyService.getCompanyIdByAccountId(accountId);
        return Result.success(companyId);
    }
}
