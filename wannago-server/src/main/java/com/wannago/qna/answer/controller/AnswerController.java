package com.wannago.qna.answer.controller;

import com.wannago.common.exception.CustomErrorCode;
import com.wannago.common.exception.CustomException;
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

    // 답변 채택
    @PostMapping("/{qnaId}/answer/{answerId}")
    public ResponseEntity<AnswerResponse> acceptAnswer(
            @PathVariable Long qnaId,
            @PathVariable Long answerId
            ) {

        // 로그인 정보 가져오기
        String loginId = getCurrentLoginId();

        AnswerResponse response = answerService.acceptAnswer(answerId, loginId);
        return ResponseEntity.ok(response);
    }

    // 현재 로그인한 ID 조회
    private String getCurrentLoginId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new CustomException(CustomErrorCode.MEMBER_NOT_EXIST);
        }
        return authentication.getName();
    }
}