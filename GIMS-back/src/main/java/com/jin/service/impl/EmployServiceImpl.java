package com.jin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jin.dto.EmployDetailDto;
import com.jin.entity.Employ;
import com.jin.mapper.EmployMapper;
import com.jin.service.EmployService;
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
public class EmployServiceImpl extends ServiceImpl<EmployMapper, Employ> implements EmployService {

    private final EmployMapper employMapper;

    public EmployServiceImpl(EmployMapper employMapper) {
        this.employMapper = employMapper;
    }

    @Override
    public List<EmployDetailDto> getDetailedInfo(String employName) {
        return employMapper.queryDetailedInfo(employName);
    }
}
