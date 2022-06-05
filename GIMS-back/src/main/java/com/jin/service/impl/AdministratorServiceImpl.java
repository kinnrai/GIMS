package com.jin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jin.entity.Administrator;
import com.jin.mapper.AdministratorMapper;
import com.jin.service.AdministratorService;
import org.springframework.stereotype.Service;

@Service
public class AdministratorServiceImpl extends ServiceImpl<AdministratorMapper, Administrator> implements AdministratorService {
}
