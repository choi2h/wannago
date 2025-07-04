package com.wannago.member.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MemberUpdateRequest {
    private String name;
    private String password;
    private String passwordConfirm;
    private String email;
}