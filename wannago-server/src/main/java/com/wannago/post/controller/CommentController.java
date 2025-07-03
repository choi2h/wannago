package com.wannago.post.controller;

import com.wannago.member.entity.Member;
import com.wannago.post.dto.CommentRequest;
import com.wannago.post.dto.CommentResponse;
import com.wannago.post.service.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
        return ResponseEntity.status(HttpStatus.CREATED).body(commentService.addComment(postId,req,member));
    }

    // 대댓글 작성
    @PostMapping("/{parentId}/reply")
    public ResponseEntity<CommentResponse> addReply(
            @PathVariable Long postId,
            @PathVariable String parentId,
            @Valid @RequestBody  CommentRequest req, // 댓글 글자수 제한 및 빈 값 유효성 검증
            @AuthenticationPrincipal Member member
    ){
        return ResponseEntity.status(HttpStatus.CREATED).body(commentService.addReply(postId,parentId,req,member));
    }

    // 댓글 전체 조회(대댓글 포함)
    @GetMapping
    public ResponseEntity<List<CommentResponse>> getAllCommentsWithReplies(
            @PathVariable Long postId
    ) {
        // 모든 댓글 (대댓글 포함)을 가져오기
        List<CommentResponse> comments = commentService.getAllCommentsWithReplies(postId);
        return ResponseEntity.ok(comments);
    }

    // 특정 댓글의 대댓글만 조회
    @GetMapping("/{parentId}/reply")
    public ResponseEntity<List<CommentResponse>> getRepliesForComment(
            @PathVariable Long postId, // 경로 일관성을 위해 postId를 유지
            @PathVariable String parentId
    ) {
        // 특정 부모 댓글에 속하는 대댓글 목록을 가져오기
        List<CommentResponse> replies = commentService.getRepliesForComment(parentId);
        return ResponseEntity.ok(replies);
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


    //댓글 삭제
    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> deleteComment(
            @PathVariable Long postId,
            @PathVariable String commentId,
            @AuthenticationPrincipal Member member
    ){
        commentService.deleteComment(postId,commentId,member);
        return ResponseEntity.noContent().build();
    }

}
