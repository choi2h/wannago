package com.wannago.qna.controller;

import com.wannago.dto.AskRequestDto;
import com.wannago.dto.AskResponseDto;
import com.wannago.service.AskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity; // ResponseEntity를 import 합니다.
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/qna")
@RequiredArgsConstructor
public class AskController {

    private final AskService askService;

    //질문 등록
    @PostMapping
    public ResponseEntity<AskResponseDto> createAsk(@RequestBody AskRequestDto requestDto) {
        // askService에서 생성된 DTO를 '쟁반'에 담아 성공(OK) 상태와 함께 반환합니다.
        return ResponseEntity.ok(askService.createAsk(requestDto));
    }

    //질문 수정
    @PutMapping("/{id}")
    public ResponseEntity<AskResponseDto> updateAsk(@PathVariable Long id, @RequestBody AskRequestDto requestDto) {
        return ResponseEntity.ok(askService.updateAsk(id, requestDto));
    }

    //질문 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAsk(@PathVariable Long id) {
        askService.deleteAsk(id);
        return ResponseEntity.ok("질문이 삭제되었습니다.");
    }

    //질문 목록 조회
    @GetMapping
    public ResponseEntity<List<AskResponseDto>> getAsks() {
        return ResponseEntity.ok(askService.getAsks());
    }

    //질문 상세 조회
    @GetMapping("/{id}")
    public ResponseEntity<AskResponseDto> getAsk(@PathVariable Long id) {
        return ResponseEntity.ok(askService.getAsk(id));
    }
}