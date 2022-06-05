package com.jin.service;

import com.jin.entity.Company;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 金磊
 * @since 2022-05-04
 */
public interface CompanyService extends IService<Company> {

    /**
     * 根据招聘者的账号id查询所属公司的id
     * @param accountId 招聘者的账号id
     * @return java.lang.Long
     * @author 金磊
     * @since 2022/5/6 15:36
     */
    Long getCompanyIdByAccountId(Long accountId);
}
