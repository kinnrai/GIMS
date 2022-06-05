package com.jin.service.impl;

import com.jin.dto.StudentDetailDto;
import com.jin.entity.Student;
import com.jin.mapper.StudentMapper;
import com.jin.service.StudentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author 金磊
 * @since 2022-05-04
 */
@Service
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student> implements StudentService {

    private final StudentMapper studentMapper;

    @Autowired
    public StudentServiceImpl(StudentMapper studentMapper) {
        this.studentMapper = studentMapper;
    }

    @Override
    public List<Student> getUnemployed(String name, Long id) {
        return studentMapper.queryUnemployed(name,id);
    }

    @Override
    public List<Student> getEmployed(String name,Long id) {
        return studentMapper.queryEmployed(name,id);
    }

    @Override
    public StudentDetailDto getDetailedInfo(Long id) {
        return studentMapper.queryDetailedInfo(id);
    }

    @Override
    public void modifyDetailedInfo(StudentDetailDto dto) {
        studentMapper.modifyDetailedInfo(dto);
    }
}
