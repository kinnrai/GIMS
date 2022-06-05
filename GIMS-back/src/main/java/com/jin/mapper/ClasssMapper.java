package com.jin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jin.entity.Classs;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 金磊
 * @since 2022-05-04
 */
public interface ClasssMapper extends BaseMapper<Classs> {
    List<Classs> queryOwnClass(Long id);
}
