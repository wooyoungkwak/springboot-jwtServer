package com.young.jwtserver.controller.home;

import com.google.common.collect.Maps;
import com.young.jwtserver.controller.ExtendsController;
import com.young.jwtserver.jwt.JwtTokenProvider;
import com.young.jwtserver.jwt.enums.ErrorCode;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * Date : 2022-05-14
 * Author : zilet
 * Project : jwtServer
 * Description :
 */
@Controller
public class homeController extends ExtendsController {

    @RequestMapping("/home")
    public String home(HttpServletRequest request, HttpServletResponse response){

        HashMap<String, Object> parameterMap = Maps.newHashMap();
        Enumeration<String> parameterNames = request.getParameterNames();
        while (parameterNames.hasMoreElements()) {
            String name = parameterNames.nextElement();
            String value = request.getParameter(name);
            parameterMap.put(name, value);
        }

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

            Cookie cookie = new Cookie("uToken", token);
            response.addCookie(cookie);
        }

        return getPath("/home");
    }

    @RequestMapping("/404")
    public String _404(HttpServletRequest request, Model model){
        model.addAttribute("message", request.getParameter("message"));
        return "/controller/error/404";
    }

}
