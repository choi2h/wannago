package com.wannago.member.controller;

import com.wannago.common.exception.CustomErrorCode;
import com.wannago.common.exception.CustomException;
import com.wannago.member.dto.JoinRequest;
import com.wannago.member.dto.LoginRequest;
import com.wannago.member.dto.MemberResponse;
import com.wannago.member.dto.TokenResponse;
import com.wannago.member.jwt.JwtTokenResolver;
import com.wannago.member.service.AuthServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthServiceImpl authServiceImpl;
    private final JwtTokenResolver jwtTokenResolver;

    @PostMapping("/join")
    public ResponseEntity<MemberResponse> join(@RequestBody JoinRequest joinRequest) {
        MemberResponse memberResponse = authServiceImpl.join(joinRequest);
        return ResponseEntity.ok(memberResponse);
    }

    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(@RequestBody LoginRequest loginRequest) {
        TokenResponse tokenResponse = authServiceImpl.login(loginRequest);
        return ResponseEntity.ok(tokenResponse);
    }

    @PostMapping("/reissue")
    public ResponseEntity<TokenResponse> reissue(HttpServletRequest httpServletRequest) {
        String refreshToken = jwtTokenResolver.resolveRefreshToken(httpServletRequest);
        String accessToken = jwtTokenResolver.resolveAccessToken(httpServletRequest);
        TokenResponse tokenResponse = authServiceImpl.reissue(refreshToken, accessToken);
        return ResponseEntity.ok(tokenResponse);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest httpServletRequest) {
        String accessToken = jwtTokenResolver.resolveAccessToken(httpServletRequest);

        if (!StringUtils.hasText(accessToken) || !authServiceImpl.validateAccessToken(accessToken)) {
            throw new CustomException(CustomErrorCode.INVALID_TOKEN);
        }

        String loginId = authServiceImpl.extractLoginId(accessToken);

        authServiceImpl.logout(loginId);

        return ResponseEntity.ok("로그아웃 되었습니다.");
    }
}
