package com.wannago.member.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class JoinRequest {
    private String name;
    private String password;
    private String confirmPassword;
    private String email;
    private String birth;
}
