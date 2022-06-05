package com.jin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jin.dto.DeliverDetailDto;
import com.jin.entity.Deliver;
import com.jin.mapper.DeliverMapper;
import com.jin.service.DeliverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeliverServiceImpl extends ServiceImpl<DeliverMapper, Deliver> implements DeliverService {

    private final DeliverMapper deliverMapper;

    @Autowired
    public DeliverServiceImpl(DeliverMapper deliverMapper){
        this.deliverMapper=deliverMapper;
    }

    @Override
    public List<Deliver> getDeliverByStudentId(Long id) {
        return deliverMapper.queryDeliverByStudentId(id);
    }

    @Override
    public List<DeliverDetailDto> getDetailedInfo(Long id) {
        return deliverMapper.queryDetailedInfo(id);
    }
}
