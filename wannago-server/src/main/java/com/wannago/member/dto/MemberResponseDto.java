package com.wannago.member.dto;

import com.wannago.member.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MemberResponseDto {
    private Long id;
    private String name;
    private String email;

    public static MemberResponseDto of(Member member) {
        return new MemberResponseDto(
                member.getId(),
                member.getName(),
                member.getEmail()
        );
    }
}
