package com.wannago.qna.answer.controller;

import com.wannago.qna.answer.dto.AnswerRequest;
import com.wannago.qna.answer.dto.AnswerResponse;
import com.wannago.qna.answer.service.AnswerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/qna")
@RequiredArgsConstructor
public class AnswerController {
  
    private final AnswerService answerService;

    // 답변 수정
    @PutMapping("/{qnaId}/answer/{answerId}")
    public ResponseEntity<AnswerResponse> updateAnswer(
            @PathVariable Long qnaId,
            @PathVariable Long answerId,
       @RequestBody AnswerRequest request
    ) {
        // 로그인 정보 가져오기
        String loginId = getCurrentLoginId();
        AnswerResponse response = answerService.updateAnswer(answerId,loginId, request);
        return ResponseEntity.ok(response);
    }
  
    // 답변 등록
    @PostMapping("/{qnaId}/answer")
    public ResponseEntity<AnswerResponse> createAnswer(
            @PathVariable Long qnaId,
            @RequestBody AnswerRequest request
    ) {
        // 로그인 정보 가져오기
        String loginId = getCurrentLoginId();
        AnswerResponse response = answerService.createAnswer(qnaId,loginId, request);
        return ResponseEntity.ok(response);
    }

    // 현재 로그인한 ID 조회
    private String getCurrentLoginId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            // 예외 처리
        }
        return authentication.getName();
    }
}