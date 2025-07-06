package com.wannago.qna.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wannago.member.entity.Member;
import com.wannago.qna.dto.MyAskResponse;
import com.wannago.qna.service.AskService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/mypage")
public class MyAskController {

    private final AskService myaskService;

    // 내가 쓴 질문 목록 조회
    @GetMapping("/qnas")
    public ResponseEntity<List<MyAskResponse>> getMyQnas(@AuthenticationPrincipal Member member) {
        List<MyAskResponse> qnas = myaskService.getQnasByMember(member);
        return ResponseEntity.ok(qnas);
    }
}
