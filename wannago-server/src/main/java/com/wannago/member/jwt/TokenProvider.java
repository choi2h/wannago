package com.wannago.member.jwt;

import com.wannago.common.exception.CustomErrorCode;
import com.wannago.common.exception.CustomException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

// 토큰 생성 및 검증
@Component
@RequiredArgsConstructor
public class TokenProvider {
    @Value("${jwt.secret}")
    private String secretKey;
    private final long accessTokenValidity = 1000L * 5;
    //private final long accessTokenValidity = 1000L * 60 * 30; // 30분
    private final long refreshTokenValidity = 1000L * 60 * 60 * 24 * 7; // 7일

    // 비밀 키 반환
    private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    // loginId 기반 액세스 토큰 생성
    public String generateAccessToken(String loginId) {
        return Jwts.builder()
                .setSubject(loginId) // 토큰 제목
                .setIssuedAt(new Date()) // 토큰 발급 시간
                .setExpiration(new Date(System.currentTimeMillis()+accessTokenValidity)) // 만료시간
                .signWith(getSigningKey())
                .compact(); // 생성
    }

    // refresh token 생성
    public String generateRefreshToken() {
        return Jwts.builder()
                .setIssuedAt(new Date()) // 토큰 발급 시간
                .setExpiration(new Date(System.currentTimeMillis()+refreshTokenValidity)) // 만료시간
                .signWith(getSigningKey())
                .compact(); // 생성
    }

    // JWT 토큰에서 loginId 추출
    public String getLoginIdFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)// 토큰 검증
                .getBody()
                .getSubject(); // loginId 반환
    }

    // JWT Token 유효성 검사
    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token); // 토큰 검증
            return true; // 유효한 토큰
        } catch (Exception e) {
            return false; // 유효하지 않은 토큰
        }
    }

    // 만료된 토큰에서 loginId 추출
    public String getLoginIdFromExpiredToken(String token) {
        try {
            return getLoginIdFromToken(token);
        } catch (ExpiredJwtException e) {
            return e.getClaims().getSubject();
        } catch (Exception e) {
            throw new CustomException(CustomErrorCode.INVALID_TOKEN);
        }
    }
}
