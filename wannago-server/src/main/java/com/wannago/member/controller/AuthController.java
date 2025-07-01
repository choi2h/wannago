package com.wannago.member.controller;

import com.wannago.member.dto.JoinRequestDto;
import com.wannago.member.dto.MemberResponseDto;
import com.wannago.member.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/join")
    public ResponseEntity<MemberResponseDto> join(@RequestBody JoinRequestDto joinRequestDto) {
        MemberResponseDto memberResponseDto = authService.join(joinRequestDto);
        return ResponseEntity.ok(memberResponseDto);
    }
}
