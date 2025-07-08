package com.wannago.member.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor // Lombok의 NoArgsConstructor를 사용하면 기본 생성자를 자동으로 만들어줍니다.
public class MemberUpdateRequest {
    private String password;
    private String passwordConfirm;
    private String phoneNumber; // 🚨 이 필드를 추가해야 합니다!
}