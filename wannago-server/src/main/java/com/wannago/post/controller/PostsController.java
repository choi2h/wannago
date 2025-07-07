package com.wannago.post.controller;

import com.wannago.post.dto.PostsResponse;
import com.wannago.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostsController {

    private final PostService postService;

    @GetMapping
    public ResponseEntity<PostsResponse> getRecentPosts(
            @RequestParam(required = false, defaultValue = "0", value = "page") int pageNo,
            @RequestParam(required = false, defaultValue = "createdDate", value = "criteria") String criteria) {
        PostsResponse response;
        if(criteria.equals("likeCount")) response = postService.getPostsOrderByLikeCount(pageNo);
         else response = postService.getPosts(pageNo, criteria);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{author}")
    public ResponseEntity<PostsResponse> getPostsByAuthor(@PathVariable String author,
              @RequestParam(required = false, defaultValue = "0", value = "page") int pageNo) {
        PostsResponse response = postService.getPostsByAuthor(author, pageNo);
        return ResponseEntity.ok(response);
    }
}
