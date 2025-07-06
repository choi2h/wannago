package com.wannago.post.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wannago.member.entity.Member;
import com.wannago.post.dto.MyPostResponse;
import com.wannago.post.service.MyPostService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/mypage/posts")
public class MyPostController {

    private final MyPostService myPostService;

    
    @GetMapping
    public ResponseEntity<List<MyPostResponse>> getMyPosts(@AuthenticationPrincipal Member member) {
        List<MyPostResponse> posts = myPostService.getMyPosts(member.getLoginId());
        return ResponseEntity.ok(posts);
    }
}