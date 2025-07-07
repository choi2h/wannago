package com.wannago.post.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wannago.post.dto.MyPostResponse;
import com.wannago.post.service.MyPostService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/mypage")
public class MyPostController {

    private final MyPostService myPostService;

    // ✅ 로그인 ID 기반 내가 쓴 게시글 목록 조회 (PathVariable 사용)
    @GetMapping("/{loginId}/posts")
    public ResponseEntity<List<MyPostResponse>> getMyPosts(@PathVariable String loginId) {
        List<MyPostResponse> posts = myPostService.getMyPosts(loginId);
        return ResponseEntity.ok(posts);
    }
}
