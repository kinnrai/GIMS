package com.jin.service;

import com.jin.dto.StudentDetailDto;
import com.jin.entity.Student;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 金磊
 * @since 2022-05-04
 */
public interface StudentService extends IService<Student> {
    List<Student> getUnemployed(String name,Long id);

    List<Student> getEmployed(String name,Long id);

    StudentDetailDto getDetailedInfo(Long id);

    void modifyDetailedInfo(StudentDetailDto dto);
}
