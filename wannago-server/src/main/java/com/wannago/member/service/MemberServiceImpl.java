package com.wannago.member.service;

import com.wannago.common.exception.CustomErrorCode;
import com.wannago.common.exception.CustomException;
import com.wannago.member.dto.MemberInfoResponse;
import com.wannago.member.dto.MemberUpdateRequest;
import com.wannago.member.entity.Member;
import com.wannago.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    
    public MemberInfoResponse getMyInfo(String loginId) {
        Member member = memberRepository.findByLoginId(loginId)
                .orElseThrow(() -> new CustomException(CustomErrorCode.MEMBER_NOT_EXIST));

        return MemberInfoResponse.builder()
                .name(member.getName())
                .email(member.getEmail())
                .loginId(member.getLoginId())
                .createdAt(member.getCreatedDate())
                .build();
    }

    
    public void updateMyInfo(String loginId, MemberUpdateRequest request) {
        Member member = memberRepository.findByLoginId(loginId)
                .orElseThrow(() -> new CustomException(CustomErrorCode.MEMBER_NOT_EXIST));

        // 예외 처리
        if (request.getName().isBlank() || request.getPassword().isBlank() || request.getPasswordConfirm().isBlank()) {
            throw new CustomException(CustomErrorCode.INVALID_INPUT_VALUE);
        }

        if (!request.getPassword().equals(request.getPasswordConfirm())) {
            throw new CustomException(CustomErrorCode.PASSWORD_NOT_MATCH);
        }

        if (request.getPassword().length() < 6 || request.getPassword().length() > 20) {
        throw new CustomException(CustomErrorCode.INVALID_PASSWORD_LENGTH);
        }

        // 정보 수정
        member.updateInfo(
                request.getName(),
                passwordEncoder.encode(request.getPassword()),
                request.getEmail()
        );
    }
}
