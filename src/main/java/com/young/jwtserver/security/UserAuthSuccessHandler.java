package com.young.jwtserver.security;

import com.young.jwtserver.encrypt.YoungEncoder;
import com.young.jwtserver.model.entity.login.domain.Vendor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
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

        request.getSession().setAttribute("hashcode", request.getSession().getId().hashCode());

        // Security Context 에 Authentication 객체 저장
        if (SecurityContextHolder.getContext().getAuthentication() == null)
            SecurityContextHolder.getContext().setAuthentication(authentication);

        // default targetUrl 로 이동하지 않도록 설정
        super.setAlwaysUseDefaultTargetUrl(true);

        // vendor 여부 확인
        String key = (String)request.getParameter("key");

        if (StringUtils.isNotEmpty(key) ){
            // key 값 복호화
            String descryptKey = YoungEncoder.decrypt("KNET_ENCRYPT_KEY", "KNET_ENCRYPT_IV", key);

            // key 값이 존재 하는지 체크  (vendor 전용 발행 키 테이블 체크)
            Vendor vendor = new Vendor();
            String vendorKey = YoungEncoder.decrypt("KNET_ENCRYPT_KEY", "KNET_ENCRYPT_IV", vendor.getKey());

            if (!StringUtils.equals(descryptKey, vendorKey)) {
                super.setDefaultTargetUrl("/404");
                request.setAttribute("message", "Vendor Key is Invalid");
            } else {
                // default 페이지 설정
                super.setDefaultTargetUrl("/auth");
            }
        } else {
            // default 페이지 설정
            super.setDefaultTargetUrl("/auth");
        }

        request.setAttribute("sample", "test Sample Attribute value");

        // 페이지 이동
        super.onAuthenticationSuccess(request, response, authentication);

//        // targetUrl 파라메터 이름 설정
//        super.setTargetUrlParameter("redirectUrl");

    }

}

