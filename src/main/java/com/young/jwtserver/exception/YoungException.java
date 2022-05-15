package com.young.jwtserver.exception;

/**
 * Date : 2022-05-12
 * Author : zilet
 * Project : jwtServer
 * Description :
 */
public class YoungException extends Throwable{

    public YoungException(String message){
        super(message);
    }

    public YoungException(Throwable e){
        super(e);
    }

    public YoungException(String message, Throwable e){
        super(message, e);
    }

}
