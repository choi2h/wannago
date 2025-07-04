package com.wannago.post.controller;

import com.wannago.post.dto.PostsResponse;
import com.wannago.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostsController {

    private final PostService postService;

    @GetMapping
    public ResponseEntity<PostsResponse> getRecentPosts(
            @RequestParam(required = false, defaultValue = "0", value = "page") int pageNo,
            @RequestParam(required = false, defaultValue = "createdDate", value = "criteria") String criteria) {
        PostsResponse response = postService.getPosts(pageNo, criteria);
        return ResponseEntity.ok(response);
    }
}
