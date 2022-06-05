package com.jin.config.security;

import com.jin.entity.Account;
import com.jin.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

/**
 * @author 金磊
 */
@Service
public class UserDetailServiceImpl implements UserDetailsService {

    private final AccountService accountService;

    @Autowired
    public UserDetailServiceImpl(AccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = accountService.queryAccountByUsername(username);
        if (account == null) {
            throw new UsernameNotFoundException("用户不存在");
        }
        return new AccountUser(account.getAccountId(),account.getTypeId(), account.getUsername(), account.getPassword(), new ArrayList<>());
    }
}
