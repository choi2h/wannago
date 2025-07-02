package com.wannago.member.jwt;

import com.wannago.common.exception.CustomErrorCode;
import com.wannago.common.exception.CustomException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

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
