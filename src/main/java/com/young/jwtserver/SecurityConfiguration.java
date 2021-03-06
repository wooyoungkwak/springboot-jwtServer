package com.young.jwtserver;

import com.young.jwtserver.jwt.JwtAuthenticationEntryPoint;
import com.young.jwtserver.jwt.JwtAuthenticationFilter;
import com.young.jwtserver.jwt.JwtTokenProvider;
import com.young.jwtserver.security.UserAuthFailureHandler;
import com.young.jwtserver.security.UserAuthLogoutSuccessHandler;
import com.young.jwtserver.security.UserAuthSuccessHandler;
import com.young.jwtserver.security.UserAuthenticationManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


/**
 * Date : 2022-03-07
 * Author : zilet
 * Project : sarangbang
 * Description :
 */
@Slf4j
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final UserAuthSuccessHandler userAuthSuccessHandler;
    private final UserAuthFailureHandler userAuthFailureHandler;
    private final UserAuthLogoutSuccessHandler userAuthLogoutSuccessHandler;
    private final UserAuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Override
    public void configure(WebSecurity webSecurity) {
        webSecurity.ignoring()
                .antMatchers("/resources/**");

        log.info("security configure register [ ignore : /resources/** ]");
    }

    @Override
    public void configure(HttpSecurity httpSecurity) throws Exception {
        // csrf configuration (not use)
        httpSecurity.csrf().disable();

        // X-Frame-Options configuration (not use)
        httpSecurity.headers().frameOptions().disable();

        // security Session policy
        // SessionCreationPolicy.ALWAYS : ???????????????????????? ?????? ????????? ??????
        // SessionCreationPolicy.IF_REQUIRED : ???????????????????????? ????????? ?????? (??????)
        // SessionCreationPolicy.NEVER : ???????????????????????? ?????????????????????, ????????? ???????????? ??????
        // SessionCreationPolicy.STATELESS : ???????????????????????? ????????????????????? ???????????? ??????????????? ?????? ( * JWT ??? ?????? ?????? *)
        httpSecurity.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        // ????????? ?????? (ID / PASSWORD) ?????? ????????? JwtAuthenticationFilter ??????
        httpSecurity.addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class);

        // UserAuthenticationProvider instead ..
        httpSecurity.authenticationManager(authenticationManager);

        // define authorize
        httpSecurity.authorizeRequests()
            // ????????? ?????? ??????
//            .antMatchers("/password").permitAll()
            .antMatchers("/register").permitAll()
            .antMatchers("/login").permitAll()
            .antMatchers("/oauth/authorize","/oauth/token", "/auth").permitAll()
            .and()
            .formLogin()
                .loginPage("/login")                    // login ?????????
                .loginProcessingUrl("/loginProcess")    // ????????? Form Action URL
                .usernameParameter("username")          // ????????? Form username
                .passwordParameter("password")          // ????????? Form password
                .successHandler(userAuthSuccessHandler)
                .failureHandler(userAuthFailureHandler)
                .permitAll()
            .and()
            .logout()
                .logoutUrl("/logout")
                .logoutSuccessHandler(userAuthLogoutSuccessHandler)
//                .deleteCookies("JSESSIONID")            // ?????? ??????
            .and()
            .exceptionHandling()
                .accessDeniedPage("/404");

        log.info("security configure register [HttpSecurity] ");
    }

}
