package com.young.jwtserver.jwt;

import com.google.common.collect.Maps;
import com.young.jwtserver.jwt.enums.ErrorCode;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Date : 2022-05-12
 * Author : zilet
 * Project : jwtServer
 * Description :
 */
@Slf4j
@Component
public class JwtTokenProvider {

    private static String JWT_SECRET = "secretKey";

    // 토큰 유효시간
    private final long JWT_EXPIRATION_MS = 10 * 60 * 1000L;           // 10 분

    private final long JWT_REFRESH_EXPIRATION_MS = 60 * 60 * 1000L;   // 1 시간

    // jwt 토큰 생성
    public String generateToken(Authentication authentication) {

        Date now = new Date();                                          // 현재 시간 설정
        Date expiryDate = new Date(now.getTime() + JWT_EXPIRATION_MS);  // 만료일 설정

        Map<String, Object> headers = new HashMap<>();
        headers.put("alg", "HS512");
        headers.put("type", "jwt");

        Map<String, Object> payloads = new HashMap<>();
        payloads.put("id", authentication.getPrincipal().toString());
        payloads.put("password", authentication.getCredentials().toString());
        payloads.put("authenticated", authentication.isAuthenticated());
        payloads.put("email", "zilet1234@gmail.com");

        return Jwts.builder()
                .setHeader(headers)                                     // header
                .setClaims(payloads)                                    // payload
                .setSubject("jwt_server_authentication")                // 제목 ( payload 일부 ? )
                .setIssuedAt(now)                                       // 현재 시간 기반으로 생성 ( payload 일부 ? )
                .setExpiration(expiryDate)                              // 만료 시간 세팅 ( payload 일부 ? )
                .signWith(SignatureAlgorithm.HS512, JWT_SECRET)         // 사용할 암호화 알고리즘, signature 에 들어갈 secret 값 세팅 ( payload 일부 ? )
                .compact();
    }

    public String generateRefreshToken(Authentication authentication) {
        Date now = new Date();                                                  // 현재 시간 설정
        Date expiryDate = new Date(now.getTime() + JWT_EXPIRATION_MS);  // 만료일 설정
        Date refreshDate = new Date(now.getTime() + JWT_REFRESH_EXPIRATION_MS); // 만료일 재설정

        Map<String, Object> headers = new HashMap<>();
        headers.put("alg", "HS512");
        headers.put("type", "jwt");

        Map<String, Object> payloads = new HashMap<>();
        payloads.put("id", authentication.getPrincipal().toString());
        payloads.put("password", authentication.getCredentials().toString());
        payloads.put("authenticated", authentication.isAuthenticated());
        payloads.put("refreshDate", expiryDate);
        payloads.put("email", "zilet1234@gmail.com");

        return Jwts.builder()
                .setHeader(headers)                                     // header
                .setClaims(payloads)                                    // payload
                .setSubject("jwt_server_authentication")                // 제목 ( payload 일부 ? )
                .setIssuedAt(now)                                       // 현재 시간 기반으로 생성 ( payload 일부 ? )
                .setExpiration(expiryDate)                              // 만료 시간 세팅 ( payload 일부 ? )
                .signWith(SignatureAlgorithm.HS512, JWT_SECRET)         // 사용할 암호화 알고리즘, signature 에 들어갈 secret 값 세팅 ( payload 일부 ? )
                .compact();
    }

    private Claims parseToken(String token) {
        return Jwts.parser()
                .setSigningKey(JWT_SECRET)
                .parseClaimsJws(token)
                .getBody();
    }

    // Jwt 토큰에서 아이디 추출
    public HashMap<String, Object> getParseToken(String token) {
        HashMap<String, Object> resultMap = validateToken(token);
        ErrorCode errorCode = (ErrorCode) resultMap.get("errorCode");

        if ( errorCode == ErrorCode.NONE) {
            Claims claims = (Claims) resultMap.get("claims");
             resultMap.put("userAuthentication", new UserAuthentication(claims.get("id"), claims.get("password")));
        }

        return resultMap;
    }

    // Jwt 토큰 유효성 검사
    private HashMap<String, Object> validateToken(String token) {
        Claims claims = null;
        ErrorCode errorCode;
        try {
            claims = parseToken(token);
            errorCode = ErrorCode.NONE;
        } catch (SignatureException ex) {
            log.error("Invalid JWT signature ({})", ex.getMessage());
            errorCode = ErrorCode.InvalidException;
        } catch (MalformedJwtException ex) {
            log.error("Invalid JWT token ({})", ex.getMessage());
            errorCode = ErrorCode.InvalidException;
        } catch (ExpiredJwtException ex) {
            log.error("Expired JWT token ({})", ex.getMessage());
            errorCode = ErrorCode.ExpiredJwtException;
        } catch (UnsupportedJwtException ex) {
            log.error("Unsupported JWT token ({})", ex.getMessage());
            errorCode = ErrorCode.UnsupportedJwtException;
        } catch (IllegalArgumentException ex) {
            log.error("JWT claims string is empty. ({})", ex.getMessage());
            errorCode = ErrorCode.InvalidException;
        }
        HashMap<String, Object> resultMap = Maps.newHashMap();
        resultMap.put("claims", claims);
        resultMap.put("errorCode", errorCode);

        return resultMap;
    }

}
