package com.young.jwtserver.jwt;

import com.young.jwtserver.jwt.enums.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

/**
 * Date : 2022-05-12
 * Author : zilet
 * Project : jwtServer
 * Description :
 */
@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String jwtToken = getJwtFromRequest(request); //request에서 jwt 토큰을 꺼낸다.

        if (StringUtils.isNotEmpty(jwtToken)) {

            try {

                HashMap resultMap = jwtTokenProvider.getParseToken(jwtToken);
                ErrorCode errorCode = (ErrorCode) resultMap.get("errorCode");

                if ( errorCode == ErrorCode.NONE ){

                    //1. 사용자 인증 클래스 생성
                    UserAuthentication authentication = (UserAuthentication) resultMap.get("userAuthentication");

                    //2. 기본적으로 제공한 details 세팅
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                    //3. 세션에서 계속 사용하기 위해 securityContext에 Authentication 등록
                    SecurityContextHolder.getContext().setAuthentication(authentication);

                    //6. jwtTokenProvider 정보
                    request.setAttribute("authorization", errorCode);
                    request.getRequestDispatcher("/auth").forward(request, response);

                } else {

                    request.setAttribute("authorization", errorCode);
                    filterChain.doFilter(request, response);

                }

            } catch (Exception ex) {
                logger.error("Could not set user authentication in security context", ex);
            }

        } else {
            request.setAttribute("authorization", ErrorCode.NOTNULL);
            filterChain.doFilter(request, response);
        }

    }

    private String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.isNotEmpty(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring("Bearer ".length());
        }
        return null;
    }

}
