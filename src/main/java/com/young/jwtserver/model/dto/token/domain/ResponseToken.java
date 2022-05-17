package com.young.jwtserver.model.dto.token.domain;

import lombok.Data;

/**
 * Date : 2022-05-15
 * Author : zilet
 * Project : jwtServer
 * Description :
 */
@Data
public class ResponseToken {

    private String access_token;

    private String token_type;

    private String refresh_token;

    private long expires;

    private String scope;

}
