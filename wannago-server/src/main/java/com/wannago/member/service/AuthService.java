package com.wannago.member.service;

import com.wannago.member.dto.JoinRequestDto;
import com.wannago.member.dto.LoginRequestDto;
import com.wannago.member.dto.MemberResponseDto;
import com.wannago.member.dto.TokenResponseDto;

public interface AuthService {
    MemberResponseDto join(JoinRequestDto joinRequestDto);
    TokenResponseDto login(LoginRequestDto loginRequestDto);
    TokenResponseDto reissue(String refreshToken, String accessToken);
}
