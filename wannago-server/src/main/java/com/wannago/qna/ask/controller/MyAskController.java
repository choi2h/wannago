package com.wannago.qna.ask.controller;

import com.wannago.member.entity.Member;
import com.wannago.qna.ask.dto.AsksResponse;
import com.wannago.qna.ask.service.MyAskService; // 새로 만든 서비스 import
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mypage")
@RequiredArgsConstructor
public class MyAskController { // 또는 MyPageController

    // [수정] PostService 주입 부분을 완전히 제거합니다.
    private final MyAskService myAskService;

    // [수정] '나의 질문'을 조회하는 API만 남겨둡니다.
    @GetMapping("/qnas")
    public ResponseEntity<AsksResponse> getMyQnas(@AuthenticationPrincipal Member member) {
        AsksResponse response = myAskService.getAsksByAuthor(member);
        return ResponseEntity.ok(response);
    }
}