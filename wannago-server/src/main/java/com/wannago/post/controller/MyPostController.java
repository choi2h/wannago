package com.wannago.post.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import com.wannago.member.entity.Member;
import com.wannago.post.dto.MyPostResponse;
import com.wannago.post.service.MyPostService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/mypage")
public class MyPostController {

    private final MyPostService myPostService;

    @GetMapping("/posts")
    public ResponseEntity<List<MyPostResponse>> getMyPosts(@AuthenticationPrincipal Member member) {
    List<MyPostResponse> posts = myPostService.getMyPosts(member);
    return ResponseEntity.ok(posts);
}
}
