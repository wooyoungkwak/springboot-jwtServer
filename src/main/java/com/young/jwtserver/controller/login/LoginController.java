package com.young.jwtserver.controller.login;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Maps;
import com.young.jwtserver.controller.ExtendsController;
import com.young.jwtserver.encrypt.YoungEncoder;
import com.young.jwtserver.jwt.JwtTokenProvider;
import com.young.jwtserver.jwt.enums.ErrorCode;
import com.young.jwtserver.model.entity.login.enums.GrantType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
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
@RequiredArgsConstructor
@Controller
public class LoginController extends ExtendsController {

    private final JwtTokenProvider jwtTokenProvider;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(HttpServletRequest request, HttpServletResponse response, Model model) {
        String code = (String) request.getAttribute("code");
        model.addAttribute("code", YoungEncoder.urlEncode(code));
        return getPath("/login");
    }

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String register(HttpServletRequest request, HttpServletResponse response, @RequestBody String body) {
        return getPath("/register");
    }

    @RequestMapping(value = "/auth", method = {RequestMethod.GET})
    @ResponseBody
    public JsonNode auth(HttpServletRequest request, HttpServletResponse response, @RequestParam(defaultValue = "") String code) throws IOException, ServletException {

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = null;

        ErrorCode errorCode = (ErrorCode) request.getAttribute("authorization");

        HashMap resultMap = Maps.newHashMap();
        if (ErrorCode.NONE == errorCode) {

            HashMap<String, Object> map = parseParameter(request);
            String path = (String) map.get("redirect_uri");
            if (StringUtils.isNotEmpty(path)) {
                request.getRequestDispatcher(path).forward(request, response);
                return null;
            } else {
                resultMap.put("resultCode", errorCode.getCode());
                resultMap.put("resultText", errorCode.getMessage());
            }

            String resultMapStr = objectMapper.writeValueAsString(resultMap);
            jsonNode = objectMapper.readTree(resultMapStr);

        } else if (ErrorCode.NOTNULL == errorCode) {

            HttpComponentsClientHttpRequestFactory httpComponentsClientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory();
            httpComponentsClientHttpRequestFactory.setConnectionRequestTimeout(10 * 1000);    // 연결 타임 아웃 (10초)
            httpComponentsClientHttpRequestFactory.setReadTimeout(5 * 1000);                  // 읽기 타임 아웃 (5초)
            RestTemplate restTemplate = new RestTemplate(httpComponentsClientHttpRequestFactory);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

            // 인증 서버로부터 Access Token 을 발급 받기 위해 필요한 파라미터 생성
            MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
            params.add("code", code);                                    // 승인 코드
            params.add("grant_type", "authorization_code");              // 권한 코드 승인 방식을 사용
            params.add("redirect_uri", "/auth");                         // rediect
            HttpEntity<MultiValueMap<String, String>> tokenRequest = new HttpEntity<>(params, headers);

            // Access Token 발급 요청
            ResponseEntity<String> tokenResponse = restTemplate.postForEntity("http://localhost:8080/oauth/token", tokenRequest, String.class);
            String body = tokenResponse.getBody();

            if (StringUtils.isNotEmpty(body))
                jsonNode = objectMapper.readTree(body);

        } else {

            resultMap.put("resultCode", errorCode.getCode());
            resultMap.put("resultText", errorCode.getMessage());

            String resultMapStr = objectMapper.writeValueAsString(resultMap);
            jsonNode = objectMapper.readTree(resultMapStr);
        }
        return jsonNode;
    }

    @RequestMapping(value = "/oauth/authorize", method = {RequestMethod.GET})
    public void authorize(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        HashMap parameter = parseParameter(request);

        String clientId = (String) parameter.get("clientId");
        if (StringUtils.isEmpty(clientId)) return;
        String secretKey = (String) parameter.get("secretKey");
        if (StringUtils.isEmpty(secretKey)) return;
        GrantType grantType = GrantType.valueOf( ((String) parameter.get("grant_type")).toUpperCase() );
        if (grantType == null) return;

        String code = YoungEncoder.encrypt(clientId + secretKey + ":" + grantType.name());

        request.setAttribute("redirect_uri", (String) parameter.get("redirect_uri"));
        request.setAttribute("scope", (String) parameter.get("scope"));
        request.setAttribute("code", code);

        request.getRequestDispatcher("/login").forward(request, response);

    }

    @RequestMapping(value = "/oauth/token", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public JsonNode token(HttpServletRequest request, HttpServletResponse response) throws JsonProcessingException {
        HashMap<String, Object> parameterMap = parseParameter(request);

        String code = (String) parameterMap.get("code");
        code = YoungEncoder.urlDecode(code);

        if (StringUtils.isEmpty(code)) return null;

        try {
            String descryptCode = YoungEncoder.decrypt(code);
            String grantTypeStr = descryptCode.split(":")[1];
            GrantType grantType = GrantType.valueOf(grantTypeStr);

            switch (grantType) {
                case PASSWORD:
                    break;
                case JWT:
                    break;
                default:
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return null;
        }

        Map<String, Object> resultMap = Maps.newHashMap();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String token = jwtTokenProvider.generateToken(authentication);
        String refreshToken = jwtTokenProvider.generateRefreshToken(authentication);

        resultMap.put("token", token);
        resultMap.put("refresh_token", refreshToken);
        resultMap.put("status", "success");
        resultMap.put("redirect_uri", parameterMap.get("redirect_uri"));

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonStr = objectMapper.writeValueAsString(resultMap);
        JsonNode jsonNode = objectMapper.readTree(jsonStr);

        return jsonNode;
    }

    // parameter 정보 가져오기
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

    // 쿠키 추가
    private void addCookie(HttpServletResponse response, String name, String value, String domain, String path, int maxAge) {
        Cookie cookie = new Cookie(name, value);
        cookie.setDomain(domain);
        cookie.setPath(path);
        cookie.setMaxAge(maxAge);
        response.addCookie(cookie);
    }

    // 쿠키 삭제
    private void deleteCookie(HttpServletResponse response, String name) {
        Cookie cookie = new Cookie(name, "");
        cookie.setMaxAge(0);
        cookie.setDomain("");
        cookie.setPath("/");
        response.addCookie(cookie);
    }
}
