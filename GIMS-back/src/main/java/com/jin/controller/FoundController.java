package com.jin.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jin.common.Result;
import com.jin.entity.Found;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

/**
 * @author 金磊
 */
@RequestMapping("/found")
@RestController
public class FoundController extends BaseController {

    @RequestMapping("/list")
    public Result getList(Long foundId) {
        Page<Found> pageData = foundService.page(getPage(),
                new QueryWrapper<Found>()
                        .like(foundId != null, "found_id", foundId)
        );

        return Result.success(pageData);
    }

    @GetMapping("/info/{id}")
    public Result getInfo(@PathVariable("id") Long id) {
        Found found = foundService.getById(id);
        return Result.success(found);
    }

    @RequestMapping("/add")
    public Result addFound(@RequestBody Found found) {
        foundService.save(found);
        return Result.success(found);
    }

    @RequestMapping("/update")
    public Result updateFound(@RequestBody Found found) {
        foundService.updateById(found);
        return Result.success(found);
    }

    @RequestMapping("/delete")
    public Result deleteFound(@RequestBody Long[] ids) {
        foundService.removeByIds(Arrays.asList(ids));
        return Result.success("");
    }
}
