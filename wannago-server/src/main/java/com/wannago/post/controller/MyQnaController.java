package com.wannago.qna.controller;

import com.wannago.member.entity.Member;
import com.wannago.qna.dto.AskResponse;
import com.wannago.qna.service.AskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/mypage")
public class MyQnaController {

    private final AskService askService;

    // 내가 쓴 질문 목록 조회
    @GetMapping("/qnas")
    public ResponseEntity<List<AskResponse>> getMyQnas(@AuthenticationPrincipal Member member) {
        List<AskResponse> qnas = askService.getQnasByMember(member);
        return ResponseEntity.ok(qnas);
    }
}
