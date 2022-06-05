package com.jin.service.impl;

import com.jin.entity.Classs;
import com.jin.mapper.ClasssMapper;
import com.jin.service.ClasssService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 金磊
 * @since 2022-05-04
 */
@Service
public class ClasssServiceImpl extends ServiceImpl<ClasssMapper, Classs> implements ClasssService {

    private final ClasssMapper classsMapper;

    @Autowired
    public ClasssServiceImpl(ClasssMapper classsMapper){
        this.classsMapper=classsMapper;
    }

    @Override
    public List<Classs> getOwnClass(Long id) {
        return classsMapper.queryOwnClass(id);
    }
}
