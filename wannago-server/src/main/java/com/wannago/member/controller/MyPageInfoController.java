package com.wannago.member.controller;

import com.wannago.member.dto.MemberInfoResponse;
import com.wannago.member.dto.MemberUpdateRequest;
import com.wannago.member.service.MemberService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/mypage")
public class MyPageInfoController {

    private final MemberService memberService;

    @GetMapping("/info")
public ResponseEntity<MemberInfoResponse> getMyInfo(@AuthenticationPrincipal String loginId) {
    MemberInfoResponse response = memberService.getMyInfo(loginId);
    return ResponseEntity.ok(response);
}

@PutMapping("/info")
public ResponseEntity<Void> updateMyInfo(
        @AuthenticationPrincipal String loginId,
        @RequestBody MemberUpdateRequest request
) {
    memberService.updateMyInfo(loginId, request);
    return ResponseEntity.ok().build();
}
}
