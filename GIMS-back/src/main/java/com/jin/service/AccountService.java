package com.jin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jin.entity.Account;

import java.util.List;

/**
 * <p>
 *  账号服务类
 * </p>
 *
 * @author 金磊
 * @since 2022-05-04
 */
public interface AccountService extends IService<Account> {
    /**
     * 根据用户名查找账号
     * @param username 账号名
     * @return com.jin.entity.Account
     * @author 金磊
     * @since 2022/5/4 21:50
     */
    Account queryAccountByUsername(String username);

    /**
     * 查找没有使用过的账号
     * @return java.util.List<com.jin.entity.Account>
     * @author 金磊
     * @since 2022/5/5 18:44
     */
    List<Long> queryAccountNotUse();

}
