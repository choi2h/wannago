package com.wannago.qna.ask.controller;


import com.wannago.common.exception.CustomErrorCode;
import com.wannago.qna.ask.dto.AskRequest;
import com.wannago.qna.ask.dto.AskResponse;
import com.wannago.qna.ask.dto.AsksResponse;
import com.wannago.qna.ask.service.AskService;
import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.awt.print.Pageable;
import java.util.List;

@RestController
@RequestMapping("/qnas")
@RequiredArgsConstructor
public class AsksController {

    private final AskService askService;

    //질문 목록 조회
    @GetMapping
    public ResponseEntity<AsksResponse> getAsks(@PathParam(value = "category") String category) {
        AsksResponse response = askService.getAsks(category);
        return ResponseEntity.ok(response);
    }

}