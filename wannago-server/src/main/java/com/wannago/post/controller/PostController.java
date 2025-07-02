package com.wannago.post.controller;

import com.wannago.post.dto.PostRequest;
import com.wannago.post.dto.PostResponse;
import com.wannago.post.dto.PostsResponse;
import com.wannago.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/post")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @GetMapping
    public ResponseEntity<PostsResponse> getAllPost() {
        PostsResponse response = postService.getAllPosts();
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public void addPost(@RequestBody PostRequest postRequest) {
        postService.insertPost(postRequest);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostResponse> getPost(@PathVariable Long id) {
        PostResponse response = postService.getPostById(id);

        return ResponseEntity.ok(response);
    }
}
