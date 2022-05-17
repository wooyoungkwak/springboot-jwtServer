package com.young.jwtserver.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Date : 2022-03-07
 * Author : zilet
 * Project : sarangbang
 * Description :
 */
@Slf4j
@Component
public class UserAuthSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        log.info(" success on Authentication [ ..... ] ");

        // Security Context 에 Authentication 객체 저장
        if (SecurityContextHolder.getContext().getAuthentication() == null)
            SecurityContextHolder.getContext().setAuthentication(authentication);

        // default targetUrl 로 이동하지 않도록 설정
        super.setAlwaysUseDefaultTargetUrl(true);

        // default 페이지 설정
        super.setDefaultTargetUrl("/auth?code=" + (String)request.getParameter("code"));

        // 페이지 이동
        super.onAuthenticationSuccess(request, response, authentication);

//        // targetUrl 파라메터 이름 설정
//        super.setTargetUrlParameter("redirectUrl");

    }

}

