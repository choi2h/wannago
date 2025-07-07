package com.wannago.member.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional; // 🚨 이 import 문 추가

import com.wannago.common.exception.CustomErrorCode;
import com.wannago.common.exception.CustomException;
import com.wannago.member.dto.MemberInfoResponse;
import com.wannago.member.dto.MemberUpdateRequest;
import com.wannago.member.entity.Member;
import com.wannago.member.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    // 내 정보 조회 (MemberInfoResponse에 phoneNumber 포함하도록 빌더 수정)
    public MemberInfoResponse getMyInfo(String loginId) {
        Member member = memberRepository.findByLoginId(loginId)
                .orElseThrow(() -> new CustomException(CustomErrorCode.MEMBER_NOT_EXIST));

        return MemberInfoResponse.builder()
                .loginId(member.getLoginId())
                .name(member.getName())
                .birth(member.getBirth() != null ? member.getBirth().toString() : null) // birth가 null일 경우 처리
                .email(member.getEmail())
                .phoneNumber(member.getPhoneNumber()) // 🚨 phoneNumber 필드 추가
                .createdAt(member.getCreatedDate())
                .build();
    }

    // 🚨 내 정보 수정 로직 대폭 수정
    @Transactional // 데이터 변경 작업에는 @Transactional 어노테이션을 붙여야 합니다.
    public void updateMyInfo(String loginId, MemberUpdateRequest request) {
        Member member = memberRepository.findByLoginId(loginId)
                .orElseThrow(() -> new CustomException(CustomErrorCode.MEMBER_NOT_EXIST));

        // 1. 비밀번호 업데이트 로직 (request에 비밀번호가 포함되어 있을 때만 처리)
        // 프론트에서 비밀번호를 입력하지 않았을 경우 request.getPassword()는 null 또는 빈 문자열
        if (request.getPassword() != null && !request.getPassword().isBlank()) {
            if (request.getPasswordConfirm() == null || request.getPasswordConfirm().isBlank()) {
                throw new CustomException(CustomErrorCode.INVALID_INPUT_VALUE); // 비밀번호 확인 누락 시 예외
            }
            if (!request.getPassword().equals(request.getPasswordConfirm())) {
                throw new CustomException(CustomErrorCode.PASSWORD_NOT_MATCH);
            }
            // 비밀번호 길이 유효성 검사 (프론트와 백엔드 모두에서 검증하는 것이 좋음)
            if (request.getPassword().length() < 6 || request.getPassword().length() > 20) {
                throw new CustomException(CustomErrorCode.INVALID_PASSWORD_LENGTH);
            }
            // 비밀번호 암호화 후 업데이트
            member.updatePassword(passwordEncoder.encode(request.getPassword())); // 🚨 Member Entity의 updatePassword 메서드 호출
        }

        // 2. 전화번호 업데이트 로직 (request에 phoneNumber 필드가 있을 때만 처리)
        // 프론트에서 phoneNumber를 항상 보내므로 null 체크는 필요 없을 수 있지만 방어적으로 유지
        if (request.getPhoneNumber() != null) {
            // 전화번호 유효성 검사 (필요하다면 여기에 추가)
            // 예: if (!request.getPhoneNumber().matches("^\\d{10,11}$")) {
            //     throw new CustomException(CustomErrorCode.INVALID_PHONE_NUMBER_FORMAT); // 새로운 CustomErrorCode 필요
            // }
            member.updatePhoneNumber(request.getPhoneNumber()); // 🚨 Member Entity의 updatePhoneNumber 메서드 호출
        }

        // 🚨 기존에 request에 없는 name, email 필드를 강제했던 유효성 검사 제거
        // if (request.getName().isBlank() || request.getPassword().isBlank() || request.getPasswordConfirm().isBlank()) {
        //     throw new CustomException(CustomErrorCode.INVALID_INPUT_VALUE);
        // }
        // if (request.getEmail().isBlank()) { ... }
        // 🚨 member.updateInfo() 대신 분리된 updatePassword, updatePhoneNumber 호출
    }
}