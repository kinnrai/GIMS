package com.jin.controller;


import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jin.common.Result;
import com.jin.dto.StudentDetailDto;
import com.jin.entity.Account;
import com.jin.entity.Student;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * @author 金磊
 */
@RequestMapping("/student")
@RestController
public class StudentController extends BaseController {

    @RequestMapping("/list")
    public Result getList(String studentName, Long classId) {
        Page<Student> pageData = studentService.page(getPage(),
                new QueryWrapper<Student>()
                        .like(StrUtil.isNotBlank(studentName), "student_name", studentName)
                        .eq(classId != null, "class_id", classId)
        );

        return Result.success(pageData);
    }

    @GetMapping("/info/{id}")
    public Result getInfo(@PathVariable("id") Long id) {
        Student student = studentService.getById(id);
        return Result.success(student);
    }

    @RequestMapping("/add")
    public Result addStudent(@RequestBody Student student) {
        Account account = accountService.getById(student.getAccountId());
        account.setTypeId(0);
        accountService.updateById(account);

        studentService.save(student);
        return Result.success(student);
    }

    @RequestMapping("/update")
    public Result updateStudent(@RequestBody Student student) {
        Account account = accountService.getById(student.getAccountId());
        account.setTypeId(0);
        accountService.updateById(account);

        studentService.updateById(student);
        return Result.success(student);
    }

    @RequestMapping("/delete")
    public Result deleteStudent(@RequestBody Long[] ids) {
        studentService.removeByIds(Arrays.asList(ids));
        return Result.success("");
    }

    @RequestMapping("/getAllStudent")
    public Result getAllStudent() {
        List<Student> studentList = studentService.list();
        return Result.success(studentList);
    }

    @RequestMapping("/getStudent")
    public Result getStudentByAccountId(@RequestBody Long id) {
        Student student = studentService.getOne(new QueryWrapper<Student>().eq("account_id", id));
        return Result.success(student);
    }

    @RequestMapping("/unemployed")
    public Result getUnemployed(String name, Long classId) {
        List<Student> studentList = studentService.getUnemployed(name, classId);
        return Result.success(studentList);
    }

    @RequestMapping("/employed")
    public Result getEmployed(String name, Long classId) {
        List<Student> studentList = studentService.getEmployed(name, classId);
        return Result.success(studentList);
    }

    @RequestMapping("/detail/{id}")
    public Result getDetailedInfo(@PathVariable("id") Long id) {
        StudentDetailDto detailedInfo = studentService.getDetailedInfo(id);
        return Result.success(detailedInfo);
    }

    @RequestMapping("/modify")
    public Result modifyDetailedInfo(@RequestBody StudentDetailDto dto) {
        studentService.modifyDetailedInfo(dto);
        return Result.success("");
    }
}
