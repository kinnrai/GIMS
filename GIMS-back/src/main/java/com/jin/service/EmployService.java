package com.jin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jin.dto.EmployDetailDto;
import com.jin.entity.Employ;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 金磊
 * @since 2022-05-04
 */
public interface EmployService extends IService<Employ> {
    List<EmployDetailDto> getDetailedInfo(String employName);
}
