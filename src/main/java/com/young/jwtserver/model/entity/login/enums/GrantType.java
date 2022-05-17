package com.young.jwtserver.model.entity.login.enums;

/**
 * Date : 2022-05-16
 * Author : zilet
 * Project : jwtServer
 * Description :
 */
public enum GrantType {
    PASSWORD("암호화 방식"),
    
    JWT("JWT 방식");
    
    private String Value;

    GrantType(String Value){
        this.Value = Value;
    }
    
    public String getValue() {
        return this.Value;
    }
}
