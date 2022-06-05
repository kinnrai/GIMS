package com.jin.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jin.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.ServletRequestUtils;

import javax.servlet.http.HttpServletRequest;

public class BaseController {
    @Autowired
    HttpServletRequest req;

    @Autowired
    AccountService accountService;

    @Autowired
    AdministratorService administratorService;

    @Autowired
    ClasssService classsService;

    @Autowired
    CompanyService companyService;

    @Autowired
    DeliverService deliverService;

    @Autowired
    EmployService employService;

    @Autowired
    FoundService foundService;

    @Autowired
    MajorService majorService;

    @Autowired
    ProfessionService professionService;

    @Autowired
    RecruiterService recruiterService;

    @Autowired
    SchoolService schoolService;

    @Autowired
    StudentService studentService;

    @Autowired
    TeacherService teacherService;

    @Autowired
    TypeService typeService;

    /**
     * 获取页面
     *
     * @return
     */
    public Page getPage() {
        int current = ServletRequestUtils.getIntParameter(req, "currentPage", 1);
        int size = ServletRequestUtils.getIntParameter(req, "pageSize", 10);

        return new Page(current, size);
    }
}
