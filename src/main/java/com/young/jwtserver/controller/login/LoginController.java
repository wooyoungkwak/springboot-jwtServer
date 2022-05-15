package com.young.jwtserver.controller.login;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Maps;
import com.young.jwtserver.controller.ExtendsController;
import com.young.jwtserver.encrypt.YoungEncoder;
import com.young.jwtserver.jwt.JwtTokenProvider;
import com.young.jwtserver.jwt.enums.ErrorCode;
import com.young.jwtserver.model.entity.login.domain.Vendor;
import com.young.jwtserver.model.entity.login.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * Date : 2022-05-12
 * Author : zilet
 * Project : jwtServer
 * Description :
 */
@Slf4j
@Controller
public class LoginController extends ExtendsController {

    @Autowired
    UserService userService;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(HttpServletRequest request, HttpServletResponse response) {

        HashMap<String, Object> parameterMap = parseParameter(request);
        String key = (String)parameterMap.get("key");

        if (StringUtils.isNotEmpty(key) ){

            // key 값 복호화
            String descryptKey = YoungEncoder.decrypt("KNET_ENCRYPT_KEY", "KNET_ENCRYPT_IV", key);

            // key 값이 존재 하는지 체크  (vendor 전용 발행 키 테이블 체크)
            Vendor vendor = new Vendor();
            String vendorKey = YoungEncoder.decrypt("KNET_ENCRYPT_KEY", "KNET_ENCRYPT_IV", vendor.getKey());

            if (!StringUtils.equals(descryptKey, vendorKey)) {
                return "/controller/error/404";
            }

            Cookie cookieKey = new Cookie("key", key);
            cookieKey.setPath("/login");
            response.addCookie(cookieKey);

            // vendor 전용 로그인 화면
            return getPath("/vendor/login");
        }

        return getPath("/login");
    }

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String register(HttpServletRequest request, HttpServletResponse response, @RequestBody String body) {
        return getPath("/register");
    }


    @RequestMapping(value = "/auth", method = {RequestMethod.GET})
    @ResponseBody
    public JsonNode auth(HttpServletRequest request, HttpServletResponse response) throws JsonProcessingException {
        HashMap<String, Object> parameterMap = parseParameter(request);
        Map<String, Object> resultMap = Maps.newHashMap();

        if ( parameterMap.get("unauthorization") != null) {
            resultMap.put("token", "");
            resultMap.put("status", "fail");
            resultMap.put("message", ((ErrorCode) parameterMap.get("unauthorization")).getMessage() );
        } else  {
            // SecurityContex 에서 Authentication 객체 가져오기
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            JwtTokenProvider jwtTokenProvider = new JwtTokenProvider();
            String token = jwtTokenProvider.generateToken(authentication);

            resultMap.put("token", token);
            resultMap.put("status", "success");
        }

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonStr = objectMapper.writeValueAsString(resultMap);
        JsonNode jsonNode = objectMapper.readTree(jsonStr);

        return jsonNode;
    }

    @RequestMapping(value = "/check", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public JsonNode check(HttpServletRequest request, HttpServletResponse response) throws JsonProcessingException {
        HashMap<String, Object> parameterMap = parseParameter(request);
        Map<String, Object> resultMap = Maps.newHashMap();

        if ( parameterMap.get("unauthorization") != null) {
            resultMap.put("token", "");
            resultMap.put("status", "fail");
            resultMap.put("message", ((ErrorCode) parameterMap.get("unauthorization")).getMessage() );
        } else  {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            JwtTokenProvider jwtTokenProvider = new JwtTokenProvider();
            String token = jwtTokenProvider.generateToken(authentication);
            resultMap.put("token", token);
            resultMap.put("status", "success");
        }

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonStr = objectMapper.writeValueAsString(resultMap);
        JsonNode jsonNode = objectMapper.readTree(jsonStr);

        return jsonNode;
    }


    public HashMap<String, Object> parseParameter(HttpServletRequest request) {
        HashMap<String, Object> parameterMap = Maps.newHashMap();
        Enumeration<String> parameterNames = request.getParameterNames();
        while (parameterNames.hasMoreElements()) {
            String name = parameterNames.nextElement();
            String value = request.getParameter(name);
            parameterMap.put(name, value);
        }

        return parameterMap;
    }

}
