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
public class RefreshRequestToken {

    private String refreshToken;

    private String grant_type;

    public HashMap getMapData() {
        HashMap map = Maps.newHashMap();
        map.put("refresh_token", refreshToken);
        map.put("grant_type", grant_type);
        return map;
    }

}
