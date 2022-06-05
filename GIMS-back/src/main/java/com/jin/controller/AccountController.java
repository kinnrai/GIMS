package com.jin.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jin.common.Result;
import com.jin.entity.Account;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

/**
 * @author 金磊
 */
@RequestMapping("/account")
@RestController
public class AccountController extends BaseController {

    @RequestMapping("/list")
    public Result getList(String username) {
        Page<Account> pageData = accountService.page(getPage(),
                new QueryWrapper<Account>()
                        .like(StrUtil.isNotBlank(username), "username", username)
        );

        return Result.success(pageData);
    }

    @RequestMapping("/info/{id}")
    public Result getInfo(@PathVariable("id") Long id) {
        Account account = accountService.getById(id);
        return Result.success(account);
    }

    @RequestMapping("/add")
    public Result addAccount(@RequestBody Account account) {
        for (Account eachAccount : accountService.list()) {
            if (eachAccount.getUsername().equals(account.getUsername())){
                return Result.error("账号已存在");
            }
        }
        account.setCreateTime(LocalDateTime.now());
        account.setUpdateTime(LocalDateTime.now());
        account.setPassword(new BCryptPasswordEncoder().encode("123456"));
        accountService.save(account);
        return Result.success(account);
    }

    @RequestMapping("/update")
    public Result updateAccount(@RequestBody Account account) {
        account.setUpdateTime(LocalDateTime.now());
        account.setPassword(new BCryptPasswordEncoder().encode(account.getPassword()));
        accountService.updateById(account);
        return Result.success(account);
    }

    @RequestMapping("/delete")
    public Result deleteAccount(@RequestBody Long[] ids) {
        accountService.removeByIds(Arrays.asList(ids));
        return Result.success("");
    }

    @RequestMapping("/getAccountNotUse")
    public Result getAccountNotUse() {
        List<Long> accountIds = accountService.queryAccountNotUse();
        return Result.success(accountIds);
    }
}
