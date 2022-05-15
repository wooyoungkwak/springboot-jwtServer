package com.young.jwtserver.model.entity.login.service;

import com.google.common.collect.Lists;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.young.jwtserver.encrypt.YoungEncoder;
import com.young.jwtserver.exception.EncryptedException;
import com.young.jwtserver.exception.EncryptedExceptionCode;
import com.young.jwtserver.exception.YoungException;
import com.young.jwtserver.model.entity.login.domain.QUser;
import com.young.jwtserver.model.entity.login.domain.User;
import com.young.jwtserver.model.entity.login.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * Date : 2022-03-07
 * Author : zilet
 * Project : sarangbang
 * Description :
 */
@Slf4j
@Transactional(rollbackFor = YoungException.class)
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public User get(Integer userSeq) throws YoungException {
        User user = userRepository.findById(userSeq).get();
        if (user != null) {
            try {
                user.setPassword(YoungEncoder.decrypt(user.getPassword()));
            } catch (EncryptedException e) {
                log.error("사용자 정보 가져오기 오류 : ", e);
                throw new YoungException(e.getMessage(), e);
            }
        }
        return user;
    }

    @Override
    public User get(String userName) throws YoungException {
        JPAQueryFactory query = new JPAQueryFactory(entityManager);
        return query.selectFrom(QUser.user)
                .where(QUser.user.username.eq(userName))
                .fetchOne();
    }

    @Override
    public User getByDB(String userName) {
        JPAQueryFactory query = new JPAQueryFactory(entityManager);
        return query.selectFrom(QUser.user)
                .where(QUser.user.username.eq(userName))
                .fetchOne();
    }

    @Override
    public List<User> gets() throws YoungException {
        List<User> users = userRepository.findAll();
        if (users == null) {
            users = Lists.newArrayList();
        }
        return users;
    }

    @Override
    public boolean isUser(String userName, String password) {
        JPAQueryFactory query = new JPAQueryFactory(entityManager);

        try {
            String passwordKey = YoungEncoder.encrypt(password);
            if (query.selectFrom(QUser.user)
                    .where(QUser.user.username.eq(userName))
                    .where(QUser.user.password.eq(passwordKey))
                    .fetchOne() != null) {
                return true;
            }
        } catch (EncryptedException e) {
            log.error(EncryptedExceptionCode.ENCRYPT_FAILURE.getMessage(), e);
        }
        return false;
    }

    @Override
    public boolean isUser(String userName) {
        JPAQueryFactory query = new JPAQueryFactory(entityManager);

        if (query.selectFrom(QUser.user)
                .where(QUser.user.username.eq(userName))
                .fetchOne() != null) {
            return true;
        }
        return false;
    }

    @Override
    public void add(User user) throws YoungException {
        try {
            user.setPassword(YoungEncoder.encrypt(user.getPassword()));
            userRepository.save(user);
        } catch (Exception e) {
            log.error("사용자 추가 오류 : ", e);
            throw new YoungException(e.getMessage());
        }
    }

    @Override
    public void adds(List<User> users) throws YoungException {
        try {
            userRepository.saveAll(users);
        } catch (Exception e) {
            log.error("사용자 추가 오류 : ", e);
            throw new YoungException(e.getMessage());
        }
    }

}
