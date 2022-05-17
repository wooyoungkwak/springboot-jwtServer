package com.young.jwtserver.jwt;

import com.young.jwtserver.jwt.enums.ErrorCode;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

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
public class JwtTokenProvider {

    private Claims claims;

    @Autowired
    UserDetailsService userDetailsService;

    private static final String JWT_SECRET = "secretKey";

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

    // Token 을 이용하여 JwtTokenProvider 설정
    public ErrorCode setJwtTokenProvider(String token){
        ErrorCode errorCode = validateToken(token);
        if ( errorCode == ErrorCode.NONE) {
            parseToken(token);
        }
        return errorCode;
    }

    private void parseToken(String token) {
        this.claims = Jwts.parser()
                .setSigningKey(JWT_SECRET)
                .parseClaimsJws(token)
                .getBody();
    }

    public Claims getClaims() {
        return this.claims;
    }

    public String getUserId() {
        return (String) claims.get("id");
    }

    public String getUserPassword(){
        return (String) claims.get("password");
    }

    // 만료 여부
    public boolean isExpired(){
        Date now = this.claims.getIssuedAt();
        Date expiryDate = this.claims.getExpiration();

        long diffTime =  expiryDate.getTime() - expiryDate.getTime();

        if ( diffTime > JWT_EXPIRATION_MS ) {
            return  true;
        }

        return false;
    }

    public boolean isRefreshExpired(){
        Date now = this.claims.getIssuedAt();
        Date refreshDate = (Date) this.claims.get("refreshDate");

        long diffTime =  refreshDate.getTime() - refreshDate.getTime();

        if ( diffTime > JWT_REFRESH_EXPIRATION_MS ) {
            return  true;
        }

        return false;
    }

    // Jwt 토큰에서 아이디 추출
    public String getUserIdFromJWT(String token) {
        ErrorCode errorCode = setJwtTokenProvider(token);
        if (ErrorCode.NONE == errorCode) {
            return (String) this.claims.get("id");
        } else {
            return null;
        }
    }

    // Authentication 가져오기
    public Authentication getAuthentication() {
        UserDetails userDetails = userDetailsService.loadUserByUsername(getUserId());
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    // Jwt 토큰 유효성 검사
    public ErrorCode validateToken(String token) {
        ErrorCode errorCode;
        try {
            Jwts.parser().setSigningKey(JWT_SECRET).parseClaimsJws(token);
            return ErrorCode.NONE;
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
        return errorCode;
    }

}
