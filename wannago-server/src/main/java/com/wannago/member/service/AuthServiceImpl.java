package com.wannago.member.service;

import com.wannago.common.exception.CustomErrorCode;
import com.wannago.common.exception.CustomException;
import com.wannago.member.dto.JoinRequest;
import com.wannago.member.dto.LoginRequest;
import com.wannago.member.dto.MemberResponse;
import com.wannago.member.dto.TokenResponse;
import com.wannago.member.entity.Member;
import com.wannago.member.jwt.TokenProvider;
import com.wannago.member.repository.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.DateTimeException;
import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;

    @Override
    @Transactional
    public MemberResponse join(JoinRequest joinRequest) {
        // 이름 값 체크
        if (joinRequest.getName() == null || joinRequest.getName().trim().isEmpty() || !joinRequest.getName().matches("^[가-힣a-zA-Z0-9]{3,12}$")) {
            throw new CustomException(CustomErrorCode.INVALID_INPUT);
        }

        if (memberRepository.existsByName(joinRequest.getName()))
            throw new CustomException(CustomErrorCode.DUPLICATE_NAME);

        // 비밀번호 값 체크
        if (joinRequest.getPassword() == null || joinRequest.getPassword().length() < 6 || joinRequest.getPassword().length() > 20) {
            throw new CustomException(CustomErrorCode.INVALID_INPUT);
        }

        if (!joinRequest.getPassword().equals(joinRequest.getConfirmPassword())) {
            throw new CustomException(CustomErrorCode.PASSWORD_MISMATCH);
        }

        // 이메일 값 체크
        if (joinRequest.getEmail() == null || !isValidEmail(joinRequest.getEmail())) {
            throw new CustomException(CustomErrorCode.INVALID_INPUT);
        }

        if (memberRepository.existsByEmail(joinRequest.getEmail())) {
            throw new CustomException(CustomErrorCode.DUPLICATE_EMAIL);
        }

        LocalDate birthDate = validateAndParseBirth(joinRequest.getBirth());
        String email = joinRequest.getEmail();
        String loginId = email.substring(0, email.indexOf("@"));

        // loginId 중복 검사
        if (memberRepository.existsByLoginId(loginId)) {
            throw new CustomException(CustomErrorCode.DUPLICATE_LOGINID);
        }

        String encodedPassword = passwordEncoder.encode(joinRequest.getPassword());

        Member member = Member.builder()
                .name(joinRequest.getName())
                .email(email)
                .loginId(loginId)
                .password(encodedPassword)
                .birth(birthDate)
                .build();

        Member savedMember = memberRepository.save(member);

        return MemberResponse.of(savedMember);
    }

    @Override
    @Transactional
    public TokenResponse login(LoginRequest loginRequest) {
        // 회원 조회
        Member member = memberRepository.findByLoginId(loginRequest.getLoginId())
                .orElseThrow(() -> new CustomException(CustomErrorCode.MEMBER_NOT_FOUND));

        // 비밀번호 검증
        if (!passwordEncoder.matches(loginRequest.getPassword(), member.getPassword())) {
            throw new CustomException(CustomErrorCode.PASSWORD_NOT_MATCH);
        }

        // 토큰 발급
        String accessToken = tokenProvider.generateAccessToken(member.getLoginId());
        String refreshToken = tokenProvider.generateRefreshToken();
        return new TokenResponse(accessToken, refreshToken, member.getLoginId());
    }

    @Override
    @Transactional
    public TokenResponse reissue(String refreshToken, String accessToken) {
        // 토큰 유효성 확인
        if (!tokenProvider.validateToken(refreshToken)) {
            throw new CustomException(CustomErrorCode.INVALID_REFRESH_TOKEN);
        }

        String loginId;
        try {
            // 만료된 토큰에서 loginId 추출
            loginId = tokenProvider.getLoginIdFromExpiredToken(accessToken);
        } catch (Exception e) {
            throw new CustomException(CustomErrorCode.INVALID_TOKEN);
        }

        // 새 토큰 발급
        String newAccessToken = tokenProvider.generateAccessToken(loginId);
        String newRefreshToken = tokenProvider.generateRefreshToken();
        return new TokenResponse(newAccessToken, newRefreshToken, loginId);
    }

    @Override
    public boolean checkEmail(String email) {
        return memberRepository.existsByEmail(email);
    }

    private boolean isValidEmail(String email) {
        // 아이디 부분(앞부분) 영문/숫자만 허용
        String[] parts = email.split("@");
        if (parts.length != 2)
            throw new CustomException(CustomErrorCode.EMAIL_MISMATCH);

        String idPart = parts[0];
        if (!idPart.matches("^[a-zA-Z0-9]+$")) {
            throw new CustomException(CustomErrorCode.EMAIL_MISMATCH);
        }

        // 간단한 도메인 형식 체크
        String domainPart = parts[1];
        if (!domainPart.matches("^[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$")) {
            throw new CustomException(CustomErrorCode.EMAIL_MISMATCH);
        }

        return true;
    }

    // 생년월일 YYYYMMDD 형식 검증 및 LocalDate 변환
    private LocalDate validateAndParseBirth(String birth) {
        if (!birth.matches("^\\d{8}$")) {
            throw new CustomException(CustomErrorCode.INVALID_INPUT);
        }

        int year = Integer.parseInt(birth.substring(0, 4));
        int month = Integer.parseInt(birth.substring(4, 6));
        int day = Integer.parseInt(birth.substring(6, 8));

        int currentYear = LocalDate.now().getYear();

        if (year < 1900 || year > currentYear - 1) {
            throw new CustomException(CustomErrorCode.INVALID_INPUT);
        }

        try {
            return LocalDate.of(year, month, day);
        } catch (DateTimeException e) {
            throw new CustomException(CustomErrorCode.INVALID_INPUT);
        }
    }
}
