package com.jin.service.impl;

import com.jin.dto.RecruiterDetailDto;
import com.jin.entity.Recruiter;
import com.jin.mapper.RecruiterMapper;
import com.jin.service.RecruiterService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 金磊
 * @since 2022-05-04
 */
@Service
public class RecruiterServiceImpl extends ServiceImpl<RecruiterMapper, Recruiter> implements RecruiterService {

    private final RecruiterMapper recruiterMapper;

    @Autowired
    public RecruiterServiceImpl(RecruiterMapper recruiterMapper){
        this.recruiterMapper=recruiterMapper;
    }

    @Override
    public RecruiterDetailDto getDetailInfo(Long id) {
        return recruiterMapper.queryDetailedInfo(id);
    }

    @Override
    public void modifyDetailedInfo(RecruiterDetailDto dto) {
        recruiterMapper.modifyDetailInfo(dto);
    }
}
