package com.young.jwtserver.model.dto.token.domain;

import com.google.common.collect.Maps;
import lombok.Data;

import java.util.HashMap;

/**
 * Date : 2022-05-15
 * Author : zilet
 * Project : jwtServer
 * Description :
 */
@Data
public class AccessRequestToken {
    private String code;
    private String grant_type;
    private String redirect_uri;

    public HashMap<String, String> getResult(){
        HashMap<String, String> map = Maps.newHashMap();
        map.put("code", this.code);
        map.put("grant_type", this.grant_type);
        map.put("redirect_uri", this.redirect_uri);

        return map;
    }
}
