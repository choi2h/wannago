package com.wannago.qna.answer.controller;

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

    // 답변 삭제
    @DeleteMapping("/{qnaId}/answers/{answerId}")
    public ResponseEntity<Void> deleteAnswer(
            @PathVariable Long qnaId,
            @PathVariable Long answerId
            ) {

        // 로그인 확인
        String loginId = getCurrentLoginId();

        answerService.deleteAnswer(answerId, loginId);
        return ResponseEntity.ok().build();
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