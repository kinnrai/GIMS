package com.jin.service;

import com.jin.dto.RecruiterDetailDto;
import com.jin.entity.Recruiter;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 金磊
 * @since 2022-05-04
 */
public interface RecruiterService extends IService<Recruiter> {

    /**
     * 获得招聘者详细信息
     * @param id 招聘者账号id
     * @return com.jin.dto.RecruiterDetailDto
     * @author 金磊
     * @since 2022/5/6 20:25
     */
    RecruiterDetailDto getDetailInfo(Long id);

    /**
     * 修改招聘者详细信息
     * @param dto 招聘者详细信息
     * @author 金磊
     * @since 2022/5/6 20:46
     */
    void modifyDetailedInfo(RecruiterDetailDto dto);
}
