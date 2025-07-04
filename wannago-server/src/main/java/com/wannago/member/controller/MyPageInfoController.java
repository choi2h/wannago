package com.wannago.member.controller;

import com.wannago.member.dto.MemberInfoResponse;
import com.wannago.member.dto.MemberUpdateRequest;
import com.wannago.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/mypage")
public class MyPageInfoController {

    private final MemberService memberService;

    // 내 정보 조회
    @GetMapping("/info")
    public ResponseEntity<MemberInfoResponse> getMyInfo(@RequestParam String loginId) {
        MemberInfoResponse response = memberService.getMyInfo(loginId);
        return ResponseEntity.ok(response);
    }

    // 내 정보 수정
    @PutMapping("/info")
    public ResponseEntity<Void> updateMyInfo(
            @RequestParam String loginId,
            @RequestBody MemberUpdateRequest request
    ) {
        memberService.updateMyInfo(loginId, request);
        return ResponseEntity.ok().build(); // 아무 응답 없이 200 OK
    }
}
