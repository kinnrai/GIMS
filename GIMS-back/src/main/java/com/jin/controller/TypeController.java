package com.jin.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jin.common.Result;
import com.jin.entity.Type;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 金磊
 */
@RequestMapping("/type")
@RestController
public class TypeController extends BaseController{

    @RequestMapping("/list")
    public Result getList(String typeName) {
        System.out.println(typeName);
        Page<Type> pageData = typeService.page(getPage(),
                new QueryWrapper<Type>()
                        .eq(StrUtil.isNotBlank(typeName), "type_name", typeName)
        );

        return Result.success(pageData);
    }

}
