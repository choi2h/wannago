package com.wannago.post.controller;

import com.wannago.member.entity.Member;
import com.wannago.post.dto.PostsResponse;
import com.wannago.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/mypage")
public class MyPostController {

    private final PostService postService;

    @GetMapping("/posts")
    public ResponseEntity<PostsResponse> getMyPosts(
            @RequestParam(required = false, defaultValue = "0", value = "page") int pageNo,
            @AuthenticationPrincipal Member member) {
        PostsResponse response = postService.getMyPosts(pageNo, member);
        return ResponseEntity.ok(response);
    }
}
