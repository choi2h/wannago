package com.wannago.post.controller;

import com.wannago.post.dto.PostResponse;
import com.wannago.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class MyPageController {

    private final PostService postService;

     // 내가 쓴 게시글 조회
    @GetMapping("/posts")
    public ResponseEntity<List<PostResponse>> getMyPosts(@RequestParam String loginId) {
        List<PostResponse> posts = postService.getMyPosts(loginId);
        return ResponseEntity.ok(posts);
    }
}
