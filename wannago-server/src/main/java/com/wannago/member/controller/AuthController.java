package com.wannago.member.controller;

import com.wannago.common.exception.CustomErrorCode;
import com.wannago.common.exception.CustomException;
import com.wannago.member.dto.JoinRequestDto;
import com.wannago.member.dto.LoginRequestDto;
import com.wannago.member.dto.MemberResponseDto;
import com.wannago.member.dto.TokenResponseDto;
import com.wannago.member.service.AuthServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
public class AuthController {

    private final AuthServiceImpl authServiceImpl;

    @PostMapping("/join")
    public ResponseEntity<MemberResponseDto> join(@RequestBody JoinRequestDto joinRequestDto) {
        MemberResponseDto memberResponseDto = authServiceImpl.join(joinRequestDto);
        return ResponseEntity.ok(memberResponseDto);
    }

    @PostMapping("/login")
    public ResponseEntity<TokenResponseDto> login(@RequestBody LoginRequestDto loginRequestDto) {
        TokenResponseDto tokenResponseDto = authServiceImpl.login(loginRequestDto);
        return ResponseEntity.ok(tokenResponseDto);
    }

    @PostMapping("/reissue")
    public ResponseEntity<TokenResponseDto> reissue(HttpServletRequest httpServletRequest) {
        String refreshToken = resolveRefreshToken(httpServletRequest);
        String accessToken = resolveAccessToken(httpServletRequest);
        TokenResponseDto tokenResponseDto = authServiceImpl.reissue(refreshToken, accessToken);
        return ResponseEntity.ok(tokenResponseDto);
    }

    private String resolveRefreshToken(HttpServletRequest httpServletRequest) {
        String bearerToken = httpServletRequest.getHeader("Authorization");
        if (bearerToken!=null && bearerToken.startsWith("Bearer")) {
            return bearerToken.substring(7);
        }
        throw new CustomException(CustomErrorCode.INVALID_TOKEN);
    }

    private String resolveAccessToken(HttpServletRequest httpServletRequest) {
        String token = httpServletRequest.getHeader("X-ACCESS-TOKEN");
        if (token != null && !token.isEmpty()) {
            return token;
        }
        throw new CustomException(CustomErrorCode.INVALID_TOKEN);
    }
}
