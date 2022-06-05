package com.jin.controller;


import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jin.common.Result;
import com.jin.dto.TeacherDetailDto;
import com.jin.entity.Account;
import com.jin.entity.Teacher;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RequestMapping("/teacher")
@RestController
public class TeacherController extends BaseController {

    @RequestMapping("/list")
    public Result getList(String teacherName) {
        Page<Teacher> pageData = teacherService.page(getPage(),
                new QueryWrapper<Teacher>()
                        .like(StrUtil.isNotBlank(teacherName), "teacher_name", teacherName)
        );

        return Result.success(pageData);
    }

    @GetMapping("/info/{id}")
    public Result getInfo(@PathVariable("id") Long id) {
        Teacher teacher = teacherService.getById(id);
        return Result.success(teacher);
    }

    @RequestMapping("/add")
    public Result addTeacher(@RequestBody Teacher teacher) {
        Account account = accountService.getById(teacher.getAccountId());
        account.setTypeId(1);
        accountService.updateById(account);

        teacherService.save(teacher);
        return Result.success(teacher);
    }

    @RequestMapping("/update")
    public Result updateTeacher(@RequestBody Teacher teacher) {
        Account account = accountService.getById(teacher.getAccountId());
        account.setTypeId(1);
        accountService.updateById(account);

        teacherService.updateById(teacher);
        return Result.success(teacher);
    }

    @RequestMapping("/delete")
    public Result deleteTeacher(@RequestBody Long[] ids) {
        teacherService.removeByIds(Arrays.asList(ids));
        return Result.success("");
    }

    @RequestMapping("/getAllTeacher")
    public Result getAllTeacher(){
        List<Teacher> teacherList = teacherService.list();
        return Result.success(teacherList);
    }

    @RequestMapping("/detail/{id}")
    public Result getDetailedInfo(@PathVariable("id") Long id) {
        TeacherDetailDto detailInfo = teacherService.getDetailInfo(id);
        return Result.success(detailInfo);
    }

    @RequestMapping("/modify")
    public Result modifyDetailedInfo(@RequestBody TeacherDetailDto dto){
        teacherService.modifyDetailedInfo(dto);
        return Result.success("");
    }
}
