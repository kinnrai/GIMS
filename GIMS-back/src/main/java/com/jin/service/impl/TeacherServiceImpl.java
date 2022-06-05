package com.jin.service.impl;

import com.jin.dto.TeacherDetailDto;
import com.jin.entity.Teacher;
import com.jin.mapper.TeacherMapper;
import com.jin.service.TeacherService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author 金磊
 * @since 2022-05-04
 */
@Service
public class TeacherServiceImpl extends ServiceImpl<TeacherMapper, Teacher> implements TeacherService {

    private final TeacherMapper teacherMapper;

    @Autowired
    public TeacherServiceImpl(TeacherMapper teacherMapper) {
        this.teacherMapper = teacherMapper;
    }

    @Override
    public TeacherDetailDto getDetailInfo(Long id) {
        return teacherMapper.queryDetailedInfo(id);
    }

    @Override
    public void modifyDetailedInfo(TeacherDetailDto dto) {
        teacherMapper.modifyDetailedInfo(dto);
    }
}
