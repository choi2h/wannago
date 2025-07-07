package com.wannago.qna.ask.controller;


import com.wannago.member.entity.Member;
import com.wannago.qna.ask.dto.AskRequest;
import com.wannago.qna.ask.dto.AskResponse;
import com.wannago.qna.ask.service.AskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/qna")
@RequiredArgsConstructor
public class AskController {

    private final AskService askService;

    //질문 등록
    @PostMapping
    public ResponseEntity<Long> createAsk(@RequestBody AskRequest requestDto, @AuthenticationPrincipal Member member) {
        Long id = askService.createAsk(requestDto, member);
        return ResponseEntity.ok(id);
    }

    //질문 수정
    @PutMapping("/{id}")
    public ResponseEntity<AskResponse> updateAsk(
            @PathVariable Long id, @RequestBody AskRequest requestDto, @AuthenticationPrincipal Member member) {
        return ResponseEntity.ok(askService.updateAsk(id, requestDto, member));
    }

    //질문 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAsk(@PathVariable Long id, @AuthenticationPrincipal Member member) {
        askService.deleteAsk(id, member);
        return ResponseEntity.ok("질문이 삭제되었습니다.");
    }

    //질문 상세 조회
    @GetMapping("/{id}")
    public ResponseEntity<AskResponse> getAsk(@PathVariable Long id) {
        return ResponseEntity.ok(askService.getAsk(id));
    }
}