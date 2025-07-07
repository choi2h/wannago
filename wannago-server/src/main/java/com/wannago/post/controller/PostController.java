package com.wannago.post.controller;

import com.wannago.member.entity.Member;
import com.wannago.post.dto.PostRequest;
import com.wannago.post.dto.PostResponse;
import com.wannago.post.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/post")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping
    public ResponseEntity<Long> addPost(@Valid @RequestBody PostRequest postRequest, @AuthenticationPrincipal Member member) {
        Long id = postService.insertPost(postRequest, member);
        return ResponseEntity.ok(id);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostResponse> getPostById(@PathVariable Long id, @AuthenticationPrincipal Member member) {
        PostResponse postResponse = postService.getPostById(id, member);
        return ResponseEntity.ok(postResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PostResponse> updatePost(
            @PathVariable Long id, @RequestBody PostRequest postRequest, @AuthenticationPrincipal Member member) {
        PostResponse postResponse = postService.updatePost(id, postRequest, member);
        return ResponseEntity.ok(postResponse);
    }

    @DeleteMapping("/{id}")
    public void deletePost(@PathVariable Long id, @AuthenticationPrincipal Member member) {
        postService.deletePost(id, member.getLoginId());
    }
}
