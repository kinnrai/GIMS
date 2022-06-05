package com.jin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jin.dto.DeliverDetailDto;
import com.jin.entity.Deliver;

import java.util.List;

public interface DeliverService extends IService<Deliver> {

    List<Deliver> getDeliverByStudentId(Long id);

    List<DeliverDetailDto> getDetailedInfo(Long id);
}
