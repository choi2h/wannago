package com.wannago.post.controller;

import com.wannago.post.dto.PostRequest;
import com.wannago.post.dto.PostResponse;
import com.wannago.post.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/post")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping
    public void addPost(@Valid @RequestBody PostRequest postRequest) {
        postService.insertPost(postRequest);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostResponse> getPostById(@PathVariable Long id) {
        PostResponse postResponse = postService.getPostById(id, 1L);
        return ResponseEntity.ok(postResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PostResponse> updatePost(@PathVariable Long id, @RequestBody PostRequest postRequest) {
        PostResponse postResponse = postService.updatePost(id, postRequest, 1L);
        return ResponseEntity.ok(postResponse);
    }
}
