package com.jin.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jin.common.Result;
import com.jin.dto.PassDto;
import com.jin.entity.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDateTime;

/**
 * @author 金磊
 */
@RequestMapping("/sys")
@RestController
public class AuthController extends BaseController {

    @GetMapping("/userInfo")
    public Result userInfo(Principal principal) {
        Account account = accountService.queryAccountByUsername(principal.getName());
        return Result.success(account);
    }

    @PostMapping("/updatePass")
    public Result updatePass(@Validated @RequestBody PassDto passDto, Principal principal) {

        Account account = accountService.queryAccountByUsername(principal.getName());

        boolean matches = new BCryptPasswordEncoder().matches(passDto.getOldPass(), account.getPassword());
        if (!matches) {
            return Result.error("旧密码不正确");
        }

        account.setPassword(new BCryptPasswordEncoder().encode(passDto.getNewPass()));
        account.setUpdateTime(LocalDateTime.now());

        accountService.updateById(account);
        return Result.success("");
    }

    @GetMapping("/info/student/{id}")
    public Result getStudentInfo(@PathVariable("id") Long id) {
        Student student = studentService.getById(id);
        if (student == null) {
            return Result.error("用户不存在");
        } else if (student.getAccountId() != null) {
            return Result.error("账号已存在");
        } else {
            return Result.success("");
        }
    }

    @GetMapping("/info/teacher/{id}")
    public Result getTeacherInfo(@PathVariable("id") Long id) {
        Teacher teacher = teacherService.getById(id);
        if (teacher == null) {
            return Result.error("用户不存在");
        } else if (teacher.getAccountId() != null) {
            return Result.error("账号已存在");
        } else {
            return Result.success("");
        }
    }

    @GetMapping("/info/recruiter/{id}")
    public Result getRecruiterInfo(@PathVariable("id") Long id) {
        Recruiter recruiter = recruiterService.getById(id);
        if (recruiter == null) {
            return Result.error("用户不存在");
        } else if (recruiter.getAccountId() != null) {
            return Result.error("账号已存在");
        } else {
            return Result.success("");
        }
    }

    @GetMapping("/info/admin/{id}")
    public Result getAdminInfo(@PathVariable("id") Long id) {
        Administrator administrator = administratorService.getById(id);
        if (administrator == null) {
            return Result.error("用户不存在");
        } else if (administrator.getAccountId() != null) {
            return Result.error("账号已存在");
        } else {
            return Result.success("");
        }
    }

    @RequestMapping("/register/{id}")
    public Result register(@PathVariable("id") Long id, @RequestBody Account account) {
        account.setCreateTime(LocalDateTime.now());
        account.setUpdateTime(LocalDateTime.now());
        account.setPassword(new BCryptPasswordEncoder().encode(account.getPassword()));
        accountService.save(account);
        account = accountService.getOne(new QueryWrapper<Account>().eq("username", account.getUsername()));
        if (account.getTypeId() == 0) {
            Student student = studentService.getById(id);
            student.setAccountId(account.getAccountId());
            studentService.updateById(student);
        } else if (account.getTypeId() == 1) {
            Teacher teacher = teacherService.getById(id);
            teacher.setAccountId(account.getAccountId());
            teacherService.updateById(teacher);
        } else if (account.getTypeId() == 2) {
            Recruiter recruiter = recruiterService.getById(id);
            recruiter.setAccountId(account.getAccountId());
            recruiterService.updateById(recruiter);
        } else if (account.getTypeId() == 3) {
            Administrator administrator = administratorService.getById(id);
            administrator.setAccountId(account.getAccountId());
            administratorService.updateById(administrator);
        }

        return Result.success("");
    }

    @RequestMapping("/info/checkUsername")
    public Result checkUsername(@RequestBody String username) {
        for (Account eachAccount : accountService.list()) {
            if (eachAccount.getUsername().equals(username)) {
                return Result.error("用户名已存在");
            }
        }
        return Result.success("");
    }

}
