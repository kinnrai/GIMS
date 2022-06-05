package com.jin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jin.dto.EmployDetailDto;
import com.jin.entity.Employ;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 金磊
 * @since 2022-05-04
 */
public interface EmployMapper extends BaseMapper<Employ> {

    List<EmployDetailDto> queryDetailedInfo(String employName);


}
