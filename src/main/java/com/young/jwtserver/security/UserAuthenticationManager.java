package com.young.jwtserver.security;

import com.young.jwtserver.model.entity.login.domain.User;
import com.young.jwtserver.model.entity.login.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Iterator;

/**
 * Date : 2022-03-07
 * Author : zilet
 * Project : sarangbang
 * Description :
 */
@Slf4j
@Component
public class UserAuthenticationManager implements AuthenticationManager {

    @Autowired
    UserService userService;

    @Autowired
    UserDetailsService userDetailsService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        String account = authentication.getPrincipal().toString();          // ID
        String password = authentication.getCredentials().toString();       // PASSWORD

log.info(" account = {}", account);
log.info(" password = {}", password);

        User user = (User) userDetailsService.loadUserByUsername(account);

        if( user == null || !user.isEnabled()) {
            log.info("사용자의 계정을 찾을수 없습니다.");
            throw new UsernameNotFoundException("사용자의 계정을 찾을수 없습니다.");
        }

        if ( !userService.isUser(account, password)) {
            log.info("사용자의 계정과 패스워드가 맞지 않습니다.");
            throw new BadCredentialsException("사용자의 계정과 패스워드가 맞지 않습니다.");
        }

        // 권한 정보
        Collection< ? extends GrantedAuthority> authorities = user.getAuthorities();

        Iterator< ? extends GrantedAuthority> iterator = authorities.iterator();
        while (iterator.hasNext()) {
            GrantedAuthority grantedAuthority = iterator.next();
            log.info(" **** authorities = {}", grantedAuthority.getAuthority());
        }

        return new UsernamePasswordAuthenticationToken(account, password, authorities);
    }

}
