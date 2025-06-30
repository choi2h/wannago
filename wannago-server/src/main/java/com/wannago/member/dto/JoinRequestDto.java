package com.wannago.member.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class JoinRequestDto {
    private String name;
    private String password;
    private String confirmPassword;
    private String email;
    private String birth;
}
