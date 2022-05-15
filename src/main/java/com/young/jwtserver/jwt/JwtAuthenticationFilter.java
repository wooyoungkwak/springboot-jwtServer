package com.young.jwtserver.jwt;

import com.young.jwtserver.jwt.enums.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Date : 2022-05-12
 * Author : zilet
 * Project : jwtServer
 * Description :
 */
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String jwtToken = getJwtFromRequest(request); //request에서 jwt 토큰을 꺼낸다.

        if (StringUtils.isEmpty(jwtToken)) {

//            request.setAttribute("unauthorization", ErrorCode.MalformedJwtException);
            response.sendRedirect("/login");

        }  else {

            try {

                JwtTokenProvider jwtTokenProvider = new JwtTokenProvider();

                ErrorCode errorCode = jwtTokenProvider.setJwtTokenProvider(jwtToken);

                if ( errorCode == ErrorCode.NONE ){

                    //1. jwtToken 에서 사용자 id를 꺼낸다.
                    String principal = jwtTokenProvider.getUserId();

                    //2. jwtToken 에서 사용자 password를 꺼낸다.
                    String credentials = jwtTokenProvider.getUserPassword();

                    //3. 사용자 인증 클래스 생성
                    UserAuthentication authentication = new UserAuthentication(principal, credentials);

                    //4. 기본적으로 제공한 details 세팅
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                    //5. 세션에서 계속 사용하기 위해 securityContext에 Authentication 등록
                    SecurityContextHolder.getContext().setAuthentication(authentication);

                    //6. 만료 여부
                    if ( jwtTokenProvider.isExpired() ) {

                        //6-1. 재설정 시간 만료 여부
                        if ( jwtTokenProvider.isRefresh() ) {
                            response.sendRedirect("/login");
                        } else {
                            response.sendRedirect("/auth");
                        }

                    }

                } else {
                    request.setAttribute("unauthorization", errorCode);
                }

            } catch (Exception ex) {
                logger.error("Could not set user authentication in security context", ex);
            }

        }

        filterChain.doFilter(request, response);

    }

    private String getJwtFromRequest(HttpServletRequest request) {
        String userToken = "";
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies ) {
            if (StringUtils.equals("uToken", cookie.getName()))
                userToken = cookie.getValue();
        }

        if (StringUtils.isNotEmpty(userToken)) {
            return userToken;
        }
        return null;
    }

}
