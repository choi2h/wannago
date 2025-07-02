package com.wannago.post.controller;

import com.wannago.member.entity.Member;
import com.wannago.post.dto.CommentRequest;
import com.wannago.post.dto.CommentResponse;
import com.wannago.post.service.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/post/{postId}/comment")
public class CommentController {

    private final CommentService commentService;

    // 댓글 작성
    @PostMapping
    public ResponseEntity<CommentResponse> addComment(
            @PathVariable Long postId,
            @Valid @RequestBody  CommentRequest req, // 댓글 글자수 제한 및 빈 값 유효성 검증
            @AuthenticationPrincipal Member member
            ){
        return ResponseEntity.ok(commentService.addComment(postId,req,member));
    }

    //댓글 수정
    @PutMapping("/{commentId}")
    public ResponseEntity<CommentResponse> updateComment(
            @PathVariable String commentId,
            @Valid @RequestBody CommentRequest commentRequest, // 댓글 글자수 제한 및 빈 값 유효성 검증
            @AuthenticationPrincipal Member member
    ) {
        return ResponseEntity.ok(commentService.updateComment(commentId,commentRequest,member));
    }
}
