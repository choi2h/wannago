package com.wannago.member.service;

import com.wannago.common.exception.CustomErrorCode;
import com.wannago.common.exception.CustomException;
import com.wannago.member.dto.JoinRequestDto;
import com.wannago.member.dto.LoginRequestDto;
import com.wannago.member.dto.MemberResponseDto;
import com.wannago.member.dto.TokenResponseDto;
import com.wannago.member.entity.Member;
import com.wannago.member.jwt.TokenProvider;
import com.wannago.member.repository.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;

    @Override
    @Transactional
    public MemberResponseDto join(JoinRequestDto joinRequestDto) {
        // 이름 값 체크
        if (joinRequestDto.getName() == null || joinRequestDto.getName().trim().isEmpty() || !joinRequestDto.getName().matches("^[a-zA-Z0-9]{4,12}$")) {
            throw new CustomException(CustomErrorCode.INVALID_INPUT);
        }

        if (memberRepository.existsByName(joinRequestDto.getName()))
            throw new CustomException(CustomErrorCode.DUPLICATE_NAME);

        // 비밀번호 값 체크
        if (joinRequestDto.getPassword() == null || joinRequestDto.getPassword().length() < 6 || joinRequestDto.getPassword().length() > 20) {
            throw new CustomException(CustomErrorCode.INVALID_INPUT);
        }

        if (!joinRequestDto.getPassword().equals(joinRequestDto.getConfirmPassword())) {
            throw new CustomException(CustomErrorCode.PASSWORD_MISMATCH);
        }

        // 이메일 값 체크
        if (joinRequestDto.getEmail() == null || !isValidEmail(joinRequestDto.getEmail())) {
            throw new CustomException(CustomErrorCode.INVALID_INPUT);
        }

        LocalDate birthDate = validateAndParseBirth(joinRequestDto.getBirth());

        String loginId = generateLoginId(joinRequestDto.getName());

        String encodedPassword = passwordEncoder.encode(joinRequestDto.getPassword());

        Member member = Member.builder()
                .name(joinRequestDto.getName())
                .email(joinRequestDto.getEmail())
                .loginId(loginId)
                .password(encodedPassword)
                .birth(birthDate)
                .build();

        Member savedMember = memberRepository.save(member);

        return MemberResponseDto.of(savedMember);
    }

    @Override
    @Transactional
    public TokenResponseDto login(LoginRequestDto loginRequestDto) {
        Member member = memberRepository.findByName(loginRequestDto.getName())
                .orElseThrow(() -> new CustomException(CustomErrorCode.MEMBER_NOT_FOUND));

        if (!passwordEncoder.matches(loginRequestDto.getPassword(), member.getPassword())) {
            throw new CustomException(CustomErrorCode.PASSWORD_NOT_MATCH);
        }

        String accessToken = tokenProvider.generateAccessToken(member.getLoginId());
        String refreshToken = tokenProvider.generateRefreshToken();
        return new TokenResponseDto(accessToken, refreshToken, member.getLoginId());
    }

    @Override
    @Transactional
    public TokenResponseDto reissue(String refreshToken, String accessToken) {
        if (!tokenProvider.validateToken(refreshToken)) {
            throw new CustomException(CustomErrorCode.INVALID_REFRESH_TOKEN);
        }
        String loginId;
        try {
            loginId = tokenProvider.getLoginIdFromExpiredToken(accessToken);
        } catch (Exception e) {
            throw new CustomException(CustomErrorCode.INVALID_TOKEN);
        }
        String newAccessToken = tokenProvider.generateAccessToken(loginId);
        String newRefreshToken = tokenProvider.generateRefreshToken();
        return new TokenResponseDto(newAccessToken, newRefreshToken, loginId);
    }

    private String generateLoginId(String name) {
        return name.toLowerCase() + "_" + UUID.randomUUID().toString().substring(0, 8);
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
