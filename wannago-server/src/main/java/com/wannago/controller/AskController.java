package com.wannago.controller;

import com.wannago.dto.AskRequestDto;
import com.wannago.dto.AskResponseDto;
import com.wannago.service.AskService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/asks")
@RequiredArgsConstructor
public class AskController {

    private final AskService askService;

    // 질문 등록
    @PostMapping
    public AskResponseDto createAsk(@RequestBody AskRequestDto requestDto) {
        return askService.createAsk(requestDto);
    }

    // 질문 수정
    @PutMapping("/{id}")
    public AskResponseDto updateAsk(@PathVariable Long id, @RequestBody AskRequestDto requestDto) {
        // @PathVariable로 id를, @RequestBody로 수정할 데이터를 받아옵니다.
        return askService.updateAsk(id, requestDto);
    }

    // 질문 삭제
    @DeleteMapping("/{id}")
    public String deleteAsk(@PathVariable Long id) {
        askService.deleteAsk(id);
        return "질문이 삭제되었습니다.";
    }

    // 전체 질문 목록 조회
    @GetMapping
    public List<AskResponseDto> getAsks() {
        return askService.getAsks();
    }

    // 질문 상세조회
    @GetMapping("/{id}")
    public AskResponseDto getAsk(@PathVariable Long id) {
        // 1. @PathVariable: URL 경로의 {id} 부분의 값을 Long 타입의 id 변수에 담아줍니다.
        // 2. 서비스의 getAsk(id) 메서드를 호출하여 특정 질문을 조회합니다.
        return askService.getAsk(id);
    }
}
