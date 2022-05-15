package com.young.jwtserver.jwt;

import com.young.jwtserver.jwt.enums.ErrorCode;
import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.util.StringUtil;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Date : 2022-05-12
 * Author : zilet
 * Project : jwtServer
 * Description :
 */
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @SneakyThrows
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        String exception = (String) request.getAttribute("exception");

        /**
         * 토큰이 없는 경우 예외처리
         */
        if (StringUtils.isEmpty(exception)) {
            setResponse(response, ErrorCode.UNAUTHORIZEDException);
            return;
        }

        /**
         * 토큰이 만료된 경우 예외처리
         */
        if (exception.equals("ExpiredJwtException")) {
            setResponse(response, ErrorCode.ExpiredJwtException);
            return;
        }

    }

    public void setResponse(HttpServletResponse response, ErrorCode errorCode) throws JSONException, IOException {
        JSONObject json = new JSONObject();
        response.setContentType("application/json;charset=UTF-8");
        response.setCharacterEncoding("utf-8");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        json.put("token", "");
        json.put("status", "fail");
        json.put("code", errorCode.getCode());
        json.put("message", errorCode.getMessage());
        response.getWriter().print(json);
    }

}
