package com.young.jwtserver.jwt.enums;

import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * Date : 2022-05-12
 * Author : zilet
 * Project : jwtServer
 * Description :
 */
public enum ErrorCode {

    NONE(200, "", HttpStatus.ACCEPTED),
    UsernameOrPasswordNotFoundException(400, "아이디 또는 비밀번호가 일치하지 않습니다.", HttpStatus.BAD_REQUEST),
    UNAUTHORIZEDException(401, "로그인 후 이용가능합니다.", HttpStatus.UNAUTHORIZED),
    ForbiddenException(403, "해당 요청에 대한 권한이 없습니다.", HttpStatus.FORBIDDEN),
    ExpiredJwtException(444, "기존 토큰이 만료되었습니다. 해당 토큰을 가지고 get-newtoken 링크로 이동해주세요.", HttpStatus.UNAUTHORIZED),
    InvalidException(446, "토큰이 잘 못 되었습니다.", HttpStatus.UNAUTHORIZED),
    MalformedJwtException(447, "토큰이 잘 못 되었습니다.", HttpStatus.UNAUTHORIZED),
    UnsupportedJwtException(449, "지원 하지 않는 토큰 입니다.", HttpStatus.UNAUTHORIZED),
    ReLogin(445, "모든 토큰이 만료되었습니다. 다시 로그인해주세요.", HttpStatus.UNAUTHORIZED),
    ;
    @Getter
    private int code;
    @Getter
    private String message;
    @Getter
    private HttpStatus status;

    ErrorCode(int code, String message, HttpStatus status) {
        this.code = code;
        this.message = message;
        this.status = status;
    }

}
