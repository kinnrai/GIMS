package com.jin.mapper;

import com.jin.dto.TeacherDetailDto;
import com.jin.entity.Teacher;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 金磊
 * @since 2022-05-04
 */
public interface TeacherMapper extends BaseMapper<Teacher> {

    TeacherDetailDto queryDetailedInfo(Long id);

    void modifyDetailedInfo(TeacherDetailDto dto);

}
