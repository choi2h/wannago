package com.wannago.member.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wannago.member.dto.MemberInfoResponse;
import com.wannago.member.dto.MemberUpdateRequest;
import com.wannago.member.entity.Member;
import com.wannago.member.service.MemberService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/mypage")
public class MyPageInfoController {

    private final MemberService memberService;

    // 🔐 내 정보 조회 (loginId를 프론트에서 넘기지 않고, 현재 로그인된 사용자 기준)
    @GetMapping("/info")
    public ResponseEntity<MemberInfoResponse> getMyInfo(@AuthenticationPrincipal Member member) {
        MemberInfoResponse response = memberService.getMyInfo(member.getLoginId());
        return ResponseEntity.ok(response);
    }

    // 🔐 내 정보 수정
    @PutMapping("/info")
    public ResponseEntity<Void> updateMyInfo(
            @AuthenticationPrincipal Member member,
            @RequestBody MemberUpdateRequest request
    ) {
        memberService.updateMyInfo(member.getLoginId(), request);
        return ResponseEntity.ok().build();
    }
}
