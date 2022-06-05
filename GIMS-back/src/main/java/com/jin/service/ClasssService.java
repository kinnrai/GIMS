package com.jin.service;

import com.jin.entity.Classs;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 金磊
 * @since 2022-05-04
 */
public interface ClasssService extends IService<Classs> {
    List<Classs> getOwnClass(Long id);
}
