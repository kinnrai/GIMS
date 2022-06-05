package com.jin;

import com.jin.entity.Student;
import com.jin.mapper.AccountMapper;
import com.jin.mapper.ClasssMapper;
import com.jin.mapper.CompanyMapper;
import com.jin.mapper.StudentMapper;
import com.jin.service.DeliverService;
import com.jin.service.EmployService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@Slf4j
@SpringBootTest
class ApplicationTests {

    @Autowired
    AccountMapper accountMapper;

    @Autowired
    DeliverService deliverService;

    @Autowired
    EmployService employService;

    @Autowired
    ClasssMapper classsMapper;

    @Autowired
    CompanyMapper companyMapper;

    @Autowired
    StudentMapper studentMapper;

    @Test
    void contextLoads() {
        log.info("------------------------开始-----------------------");
        List<Student> studentList = studentMapper.queryUnemployed(null, 1L);
        for (Student student : studentList) {
            System.out.println(student);
        }
        log.info("------------------------结束-------------------------");
    }

    //@Test
    //void selectListTest() {
    //    log.info("------------------------开始-----------------------");
    //    List<Account> accountList = accountMapper.selectList();
    //    for (Account account : accountList) {
    //        System.out.println(account);
    //    }
    //    log.info("------------------------结束-------------------------");
    //}
    //
    //@Test
    //void selectByIdTest() {
    //    log.info("------------------------开始-----------------------");
    //    Account account = accountMapper.selectById(1L);
    //    System.out.println(account);
    //    log.info("------------------------结束-------------------------");
    //}
    //
    //@Test
    //void insertTest() {
    //    log.info("------------------------开始-----------------------");
    //    Account account = new Account();
    //    account.setUsername("哈哈哈");
    //    account.setPassword(new BCryptPasswordEncoder().encode("123456"));
    //    account.setEmail("123456@qq.com");
    //    account.setAvatar("http://m.imeitou.com/uploads/allimg/220503/3-2205031F250.jpg");
    //    account.setTypeId(0);
    //    account.setCreateTime(LocalDateTime.now());
    //    account.setUpdateTime(LocalDateTime.now());
    //
    //    accountMapper.insert(account);
    //    log.info("------------------------结束-------------------------");
    //
    //}
    //
    //@Test
    //void updateTest() {
    //    log.info("------------------------开始-----------------------");
    //    Account account = accountMapper.selectById(29L);
    //    account.setUsername("我我我");
    //    account.setUpdateTime(LocalDateTime.now());
    //    accountMapper.update(account);
    //    log.info("------------------------结束-------------------------");
    //}
    //
    //@Test
    //void deleteTest() {
    //    log.info("------------------------开始-----------------------");
    //    accountMapper.deleteById(29L);
    //    log.info("------------------------结束-------------------------");
    //}
}
