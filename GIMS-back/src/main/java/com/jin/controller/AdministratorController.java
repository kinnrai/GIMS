package com.jin.controller;


import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jin.common.Result;
import com.jin.entity.Account;
import com.jin.entity.Administrator;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

/**
 * @author 金磊
 */
@RequestMapping("/admin")
@RestController
public class AdministratorController extends BaseController {

    @RequestMapping("/list")
    public Result getList(String administratorName) {
        Page<Administrator> pageData = administratorService.page(getPage(),
                new QueryWrapper<Administrator>()
                        .like(StrUtil.isNotBlank(administratorName), "administrator_name", administratorName)
        );

        return Result.success(pageData);
    }

    @GetMapping("/info/{id}")
    public Result getInfo(@PathVariable("id") Long id) {
        Administrator administrator = administratorService.getById(id);
        return Result.success(administrator);
    }

    @RequestMapping("/add")
    public Result addAdministrator(@RequestBody Administrator administrator) {
        Account account = accountService.getById(administrator.getAccountId());
        account.setTypeId(3);
        accountService.updateById(account);

        administratorService.save(administrator);
        return Result.success(administrator);
    }

    @RequestMapping("/update")
    public Result updateAdministrator(@RequestBody Administrator administrator) {
        Account account = accountService.getById(administrator.getAccountId());
        account.setTypeId(3);
        accountService.updateById(account);

        administratorService.updateById(administrator);
        return Result.success(administrator);
    }

    @RequestMapping("/delete")
    public Result deleteAdministrator(@RequestBody Long[] ids) {
        administratorService.removeByIds(Arrays.asList(ids));
        return Result.success("");
    }

    @RequestMapping("/getAdmin/{id}")
    public Result getAdminByAccountId(@PathVariable("id") Long id){
        Administrator admin = administratorService.getOne(new QueryWrapper<Administrator>().eq("account_id", id));
        return Result.success(admin);
    }

    @RequestMapping("/modify")
    public Result modify(@RequestBody Administrator administrator){
        administratorService.updateById(administrator);
        return Result.success("");
    }
}
