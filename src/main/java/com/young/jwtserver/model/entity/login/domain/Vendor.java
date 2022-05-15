package com.young.jwtserver.model.entity.login.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Date : 2022-05-12
 * Author : zilet
 * Project : jwtServer
 * Description :
 */
@Setter
@Getter
//@Entity
public class Vendor {

//    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer vendorInteger = 1;

//    @Column (nullable = false)
    private String vendorName;

//    @Column (nullable = false)
    private String key = "pk8Il1/x+DqaPOdQMsbZbA==";  // test : 지서기(wltjrl)

//    @Column (nullable = false)
    private LocalDateTime regDate = LocalDateTime.now();

//    @Column (nullable = false)
    private Boolean delYN = false;

//    @Column
    private LocalDateTime delDate = LocalDateTime.now();
}
