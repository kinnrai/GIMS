package com.jin.mapper;

import com.jin.dto.StudentDetailDto;
import com.jin.entity.Student;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author 金磊
 * @since 2022-05-04
 */
public interface StudentMapper extends BaseMapper<Student> {
    List<Student> queryUnemployed(@Param("name") String name, @Param("id") Long id);

    List<Student> queryEmployed(@Param("name") String name, @Param("id") Long id);

    StudentDetailDto queryDetailedInfo(Long id);

    void modifyDetailedInfo(StudentDetailDto dto);
}
