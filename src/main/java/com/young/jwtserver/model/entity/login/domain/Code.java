package com.young.jwtserver.model.entity.login.domain;

import com.young.jwtserver.model.entity.login.enums.GrantType;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * Date : 2022-05-12
 * Author : zilet
 * Project : jwtServer
 * Description : 암호화 타입
 */
@Setter
@Getter
//@Entity
public class Code {

//    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer codeInteger = 1;

//    @Column (nullable = false)
    private GrantType codeType;

//    @Column (nullable = false)
    private String value = "";  // test : 지서기(wltjrl)

//    @Column (nullable = false)
    private LocalDateTime regDate = LocalDateTime.now();

//    @Column (nullable = false)
    private Boolean delYN = false;

//    @Column
    private LocalDateTime delDate = LocalDateTime.now();
}
