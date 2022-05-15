package com.young.jwtserver.jwt;

import org.springframework.http.HttpStatus;

/**
 * Date : 2022-05-12
 * Author : zilet
 * Project : jwtServer
 * Description :
 */
public class ApiResponse {

    private int code = HttpStatus.OK.value();
    private Object result;

    public ApiResponse() {
    }

    public ApiResponse(int code, Object result) {
        this.code = code;
        this.result = result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

}
