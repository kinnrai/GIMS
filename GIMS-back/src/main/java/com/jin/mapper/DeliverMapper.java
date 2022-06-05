package com.jin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jin.dto.DeliverDetailDto;
import com.jin.entity.Deliver;

import java.util.List;

/**
 * @author 金磊
 */
public interface DeliverMapper extends BaseMapper<Deliver> {
    List<Deliver> queryDeliverByStudentId(Long id);

    List<DeliverDetailDto> queryDetailedInfo(Long id);

}
