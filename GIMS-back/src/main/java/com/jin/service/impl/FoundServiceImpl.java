package com.jin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jin.entity.Found;
import com.jin.mapper.FoundMapper;
import com.jin.service.FoundService;
import org.springframework.stereotype.Service;

@Service
public class FoundServiceImpl extends ServiceImpl<FoundMapper, Found> implements FoundService {
}
