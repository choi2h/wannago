package com.wannago.member.service;

import com.wannago.member.dto.JoinRequest;
import com.wannago.member.dto.LoginRequest;
import com.wannago.member.dto.MemberResponse;
import com.wannago.member.dto.TokenResponse;

public interface AuthService {
    MemberResponse join(JoinRequest joinRequest);
    TokenResponse login(LoginRequest loginRequest);
    TokenResponse reissue(String refreshToken, String accessToken);
}
