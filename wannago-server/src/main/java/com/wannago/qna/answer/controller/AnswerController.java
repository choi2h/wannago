package com.wannago.qna.answer.controller;

import com.wannago.qna.answer.dto.AnswerResponse;
import com.wannago.qna.answer.service.AnswerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/qna")
@RequiredArgsConstructor
public class AnswerController {
  
    private final AnswerService answerService;

    // 특정 질문의 모든 답변 조회.
    @GetMapping("/questions/{askId}/answers")
    public ResponseEntity<List<AnswerResponse>> getAnswers(@PathVariable Long askId) {
        List<AnswerResponse> answers = answerService.getAnswersByAskId(askId);
        return ResponseEntity.ok(answers);
    }
}