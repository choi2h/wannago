package com.wannago.member.controller;

import com.wannago.member.dto.*;
import com.wannago.member.jwt.JwtTokenResolver;
import com.wannago.member.service.AuthServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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
        return ResponseEntity.ok("로그아웃 성공");
    }

    @PostMapping("/check-email")
    public ResponseEntity<EmailCheckResponse> checkEmail(@RequestBody EmailCheckRequest emailCheckRequest) {
        boolean exists = authServiceImpl.checkEmail(emailCheckRequest.getEmail());
        EmailCheckResponse emailCheckResponse = new EmailCheckResponse(exists);
        return ResponseEntity.ok(emailCheckResponse);
    }
}
