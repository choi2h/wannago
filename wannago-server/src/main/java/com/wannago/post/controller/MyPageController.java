package com.wannago.post.controller;
// import org.springframework.security.core.annotation.AuthenticationPrincipal; 
// 추후에 @AuthenticationPrincipal Member member 대체

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
    @GetMapping("/posts")
    public ResponseEntity<List<PostResponse>> getMyPosts(@RequestParam String loginId) {
        List<PostResponse> posts = postService.getMyPosts(loginId);
        return ResponseEntity.ok(posts);
    }



     // 내가 쓴 게시글 조회
//     @GetMapping("/posts")
//     public ResponseEntity<List<PostResponse>> getMyPosts(@AuthenticationPrincipal Member member) {
//     List<PostResponse> posts = postService.getMyPosts(member.getLoginId());
//     return ResponseEntity.ok(posts);
// } 추후에 @AuthenticationPrincipal Member member 로 대체할 코드

}
