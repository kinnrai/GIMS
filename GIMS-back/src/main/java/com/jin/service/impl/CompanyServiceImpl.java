package com.jin.service.impl;

import com.jin.entity.Company;
import com.jin.mapper.CompanyMapper;
import com.jin.service.CompanyService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author 金磊
 * @since 2022-05-04
 */
@Service
public class CompanyServiceImpl extends ServiceImpl<CompanyMapper, Company> implements CompanyService {

    private final CompanyMapper companyMapper;

    public CompanyServiceImpl(CompanyMapper companyMapper) {
        this.companyMapper = companyMapper;
    }

    @Override
    public Long getCompanyIdByAccountId(Long accountId) {
        return companyMapper.getCompanyIdByAccountId(accountId);
    }
}
