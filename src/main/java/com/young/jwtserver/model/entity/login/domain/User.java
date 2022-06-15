package com.young.jwtserver.model.entity.login.domain;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.google.common.collect.Lists;
import com.young.jwtserver.model.entity.login.enums.Role;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;

/**
 * Date : 2022-03-06
 * Author : zilet
 * Project : jwtServer
 * Description :
 */
@Getter
@Setter
@JsonNaming(PropertyNamingStrategies.LowerCamelCaseStrategy.class)
@Entity(name = "person")
public class User implements UserDetails {

    private static final long serialVersionUID = 10001L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "personSeq")
    private Integer userSeq;

    @Column(name = "account", nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private Role role;

    @Column(nullable = false)
    private String firstName;

    @Column
    private String lastName;

    @Transient
    private boolean accountNonExpired = true;

    @Transient
    private boolean accountNonLocked = true;

    @Transient
    private boolean credentialsNonExpired = true;

    @Transient
    private boolean enabled = true;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorityList = Lists.newArrayList();
        authorityList.add(new SimpleGrantedAuthority(this.role.getValue()));
        return authorityList;
    }

}
