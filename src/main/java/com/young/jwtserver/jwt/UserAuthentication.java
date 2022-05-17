package com.young.jwtserver.jwt;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * Date : 2022-05-12
 * Author : zilet
 * Project : jwtServer
 * Description :
 */
public class UserAuthentication extends UsernamePasswordAuthenticationToken {

    public UserAuthentication(Object principal, Object credentials) {
        super(principal, credentials);
    }

    public UserAuthentication(String principal, String credentials, Collection<? extends GrantedAuthority> authorities) {
        super(principal, credentials, authorities);
    }

}

