package com.jin.service;

import com.jin.dto.TeacherDetailDto;
import com.jin.entity.Teacher;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 金磊
 * @since 2022-05-04
 */
public interface TeacherService extends IService<Teacher> {

    TeacherDetailDto getDetailInfo(Long id);

    void modifyDetailedInfo(TeacherDetailDto dto);

}
