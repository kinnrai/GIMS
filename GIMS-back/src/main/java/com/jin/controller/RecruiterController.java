package com.jin.controller;


import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jin.common.Result;
import com.jin.dto.RecruiterDetailDto;
import com.jin.entity.Account;
import com.jin.entity.Recruiter;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

@RequestMapping("/recruiter")
@RestController
public class RecruiterController extends BaseController {

    @RequestMapping("/list")
    public Result getList(String recruiterName) {
        Page<Recruiter> pageData = recruiterService.page(getPage(),
                new QueryWrapper<Recruiter>()
                        .like(StrUtil.isNotBlank(recruiterName), "recruiter_name", recruiterName)
        );

        return Result.success(pageData);
    }

    @GetMapping("/info/{id}")
    public Result getInfo(@PathVariable("id") Long id) {
        Recruiter recruiter = recruiterService.getById(id);
        return Result.success(recruiter);
    }

    @RequestMapping("/add")
    public Result addRecruiter(@RequestBody Recruiter recruiter) {
        Account account = accountService.getById(recruiter.getAccountId());
        account.setTypeId(2);
        accountService.updateById(account);

        recruiterService.save(recruiter);
        return Result.success(recruiter);
    }

    @RequestMapping("/update")
    public Result updateRecruiter(@RequestBody Recruiter recruiter) {
        Account account = accountService.getById(recruiter.getAccountId());
        account.setTypeId(2);
        accountService.updateById(account);

        recruiterService.updateById(recruiter);
        return Result.success(recruiter);
    }

    @RequestMapping("/delete")
    public Result deleteRecruiter(@RequestBody Long[] ids) {
        recruiterService.removeByIds(Arrays.asList(ids));
        return Result.success("");
    }

    @RequestMapping("/detail/{id}")
    public Result getDetailedInfo(@PathVariable("id") Long id) {
        RecruiterDetailDto detailInfo = recruiterService.getDetailInfo(id);
        return Result.success(detailInfo);
    }

    @RequestMapping("/modify")
    public Result modifyDetailedInfo(@RequestBody RecruiterDetailDto dto){
        recruiterService.modifyDetailedInfo(dto);
        return Result.success("");
    }
}
