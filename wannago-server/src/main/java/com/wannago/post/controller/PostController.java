package com.wannago.post.controller;

import com.wannago.post.dto.PostRequest;
import com.wannago.post.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/post")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping
    public void addPost(@Valid @RequestBody PostRequest postRequest) {
        System.out.println(postRequest);
        postService.insertPost(postRequest);
    }

}
