package com.jin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jin.dto.RecruiterDetailDto;
import com.jin.entity.Recruiter;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 金磊
 * @since 2022-05-04
 */
public interface RecruiterMapper extends BaseMapper<Recruiter> {

    /**
     * 获得招聘者详细信息
     * @param id 招聘者账号id
     * @return com.jin.dto.RecruiterDetailDto
     * @author 金磊
     * @since 2022/5/6 20:25
     */
    RecruiterDetailDto queryDetailedInfo(Long id);

    /**
     * 修改招聘者详细信息
     * @param dto 招聘者详细信息
     * @author 金磊
     * @since 2022/5/6 20:46
     */
    void modifyDetailInfo(RecruiterDetailDto dto);
}
