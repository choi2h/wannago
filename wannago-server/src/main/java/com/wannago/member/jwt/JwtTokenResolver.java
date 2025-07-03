package com.wannago.member.jwt;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

// 헤더에서 Token 추출
@Component
public class JwtTokenResolver {
    public String resolveRefreshToken(HttpServletRequest httpServletRequest) {
        String bearerToken = httpServletRequest.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    public String resolveAccessToken(HttpServletRequest httpServletRequest) {
        String token = httpServletRequest.getHeader("X-ACCESS-TOKEN");
        return StringUtils.hasText(token) ? token : null;
    }
}
