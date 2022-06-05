package com.jin.config.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

/**
 * @author 金磊
 */
public class AccountUser extends User {

    private Long accountId;

    private final Integer typeId;

    public AccountUser(Long accountId,Integer typeId,String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
        this.accountId=accountId;
        this.typeId=typeId;
    }

    public AccountUser(Long accountId,Integer typeId,String username, String password, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
        this.accountId=accountId;
        this.typeId=typeId;
    }
}
