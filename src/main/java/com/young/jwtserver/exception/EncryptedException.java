package com.young.jwtserver.exception;

/**
 * Date : 2022-03-14
 * Author : zilet
 * Project : sarangbang
 * Description :
 */
public class EncryptedException extends RuntimeException{

    public EncryptedException(EncryptedExceptionCode code) {
        super(code.getMessage());
    }

    public EncryptedException(EncryptedExceptionCode code, Exception e) {
        super(code.getMessage(), e);
    }

}
