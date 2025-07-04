package com.wannago.member.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberInfoResponse {
    private String loginId;       // 로그인 ID
    private String name;          // 이름
    private String email;         // 이메일 (선택 사항)
    private LocalDateTime createdAt; // 가입일
}