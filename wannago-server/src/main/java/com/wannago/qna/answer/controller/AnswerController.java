package com.wannago.qna.answer.controller;

import com.wannago.common.exception.CustomErrorCode;
import com.wannago.common.exception.CustomException;
import com.wannago.member.entity.Member;
import com.wannago.qna.answer.dto.AnswerRequest;
import com.wannago.qna.answer.dto.AnswerResponse;
import com.wannago.qna.answer.service.AnswerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/qna")
@RequiredArgsConstructor
public class AnswerController {

    private final AnswerService answerService;

    // 답변 등록
    @PostMapping("/{id}/answer")
    public ResponseEntity<AnswerResponse> createAnswer(
            @PathVariable Long id,
            @RequestBody AnswerRequest request,
            @AuthenticationPrincipal Member member
    ) {
        validateMember(member);
        AnswerResponse response = answerService.createAnswer(id, member.getLoginId(), request);
        return ResponseEntity.ok(response);
    }

    // 답변 수정
    @PutMapping("/{id}/answer/{answerId}")
    public ResponseEntity<AnswerResponse> updateAnswer(
            @PathVariable Long id,
            @PathVariable Long answerId,
            @RequestBody AnswerRequest request,
            @AuthenticationPrincipal Member member
    ) {
        validateMember(member);
        AnswerResponse response = answerService.updateAnswer(answerId, member.getLoginId(), request);
        return ResponseEntity.ok(response);
    }

    // 답변 삭제
    @DeleteMapping("/{id}/answer/{answerId}")
    public ResponseEntity<Void> deleteAnswer(
            @PathVariable Long id,
            @PathVariable Long answerId,
            @AuthenticationPrincipal Member member
    ) {
        validateMember(member);
        answerService.deleteAnswer(answerId, member.getLoginId());
        return ResponseEntity.ok().build();
    }

    // 답변 채택
    @PostMapping("/{id}/answer/{answerId}")
    public ResponseEntity<AnswerResponse> acceptAnswer(
            @PathVariable Long id,
            @PathVariable Long answerId,
            @AuthenticationPrincipal Member member
    ) {
        validateMember(member);
        AnswerResponse response = answerService.acceptAnswer(answerId, member.getLoginId());
        return ResponseEntity.ok(response);
    }

    // 특정 질문의 모든 답변 조회
    @GetMapping("/{id}/answer")
    public ResponseEntity<List<AnswerResponse>> getAnswers(@PathVariable Long id) {
        List<AnswerResponse> answers = answerService.getAnswersByAskId(id);
        return ResponseEntity.ok(answers);
    }

    // 멤버 유효성 검증
    private void validateMember(Member member) {
        if (member == null) {
            throw new CustomException(CustomErrorCode.MEMBER_NOT_EXIST);
        }
    }
}