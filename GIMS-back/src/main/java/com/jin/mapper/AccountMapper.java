package com.jin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jin.entity.Account;

import java.util.List;

/**
 * <p>
 *  账号Mapper 接口
 * </p>
 *
 * @author 金磊
 * @since 2022-05-04
 */
public interface AccountMapper extends BaseMapper<Account> {
    /**
     * 根据用户名查找账号
     * @param username 账号名
     * @return com.jin.entity.Account
     * @author 金磊
     * @since 2022/5/4 21:45
     */
    Account queryAccountByUsername(String username);

    /**
     * 查找没有使用过的账号
     * @return java.util.List<com.jin.entity.Account>
     * @author 金磊
     * @since 2022/5/5 18:43
     */
    List<Long> queryAccountNotUse();

    //int insert(Account account);
    //
    //int update(Account account);
    //
    //List<Account> selectList();
    //
    //Account selectById(Long accountId);
    //
    //int deleteById(Long accountId);
}
