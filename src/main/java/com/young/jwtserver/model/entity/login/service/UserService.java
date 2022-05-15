package com.young.jwtserver.model.entity.login.service;

import com.young.jwtserver.exception.YoungException;
import com.young.jwtserver.model.entity.login.domain.User;

import java.util.List;

/**
 * Date : 2022-03-07
 * Author : zilet
 * Project : sarangbang
 * Description :
 */
public interface UserService {

    public User get(Integer userSeq) throws YoungException;

    public User get(String userName) throws YoungException;

    public User getByDB(String userName);

    public List<User> gets() throws YoungException;

    public boolean isUser(String userName, String password);

    public boolean isUser(String userName);

    public void add(User user) throws YoungException;

    public void adds(List<User> users) throws YoungException;

}
