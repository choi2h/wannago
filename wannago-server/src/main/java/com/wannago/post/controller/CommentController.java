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
        // 로그인 안 했을 경우 더미 유저로 대체
        if (member == null) {
            member = Member.builder()
                    .id(1L)
                    .loginId("test_user")
                    .email("test@example.com")
                    .build();
        }
        return ResponseEntity.ok(commentService.addComment(postId,req,member));
    }

    // 대댓글 작성
    @PostMapping("/{parentId}/reply")
    public ResponseEntity<CommentResponse> addReply(
            @PathVariable Long postId,
            @PathVariable String parentId,
            @Valid @RequestBody  CommentRequest req, // 댓글 글자수 제한 및 빈 값 유효성 검증
            @AuthenticationPrincipal Member member
    ){
        // 로그인 안 했을 경우 더미 유저로 대체
        if (member == null) {
            member = Member.builder()
                    .id(1L)
                    .loginId("test_user")
                    .email("test@example.com")
                    .build();
        }
        return ResponseEntity.ok(commentService.addReply(postId,parentId,req,member));
    }

    // 댓글 전체 조회(대댓글 포함)
    @GetMapping
    public ResponseEntity<List<CommentResponse>> getAllCommentsWithReplies(
            @PathVariable Long postId,
            @AuthenticationPrincipal Member member
    ) {
        // 모든 댓글 (대댓글 포함)을 가져오기
        List<CommentResponse> comments = commentService.getAllCommentsWithReplies(postId);
        // 로그인 안 했을 경우 더미 유저로 대체
        if (member == null) {
            member = Member.builder()
                    .id(1L)
                    .loginId("test_user")
                    .email("test@example.com")
                    .build();
        }
        // HTTP 200 OK 상태 코드와 함께 댓글 목록을 반환
        return ResponseEntity.ok(comments);
    }

    // 특정 댓글의 대댓글만 조회
    @GetMapping("/{parentId}/reply")
    public ResponseEntity<List<CommentResponse>> getRepliesForComment(
            @PathVariable Long postId, // 경로 일관성을 위해 postId를 유지
            @PathVariable String parentId,
            @AuthenticationPrincipal Member member
    ) {
        // 특정 부모 댓글에 속하는 대댓글 목록을 가져오기
        List<CommentResponse> replies = commentService.getRepliesForComment(parentId);
        // 로그인 안 했을 경우 더미 유저로 대체
        if (member == null) {
            member = Member.builder()
                    .id(1L)
                    .loginId("test_user")
                    .email("test@example.com")
                    .build();
        }
        // HTTP 200 OK 상태 코드와 함께 대댓글 목록을 반환합니다.
        return ResponseEntity.ok(replies);
    }

    //댓글 수정
    @PutMapping("/{commentId}")
    public ResponseEntity<CommentResponse> updateComment(
            @PathVariable String commentId,
            @Valid @RequestBody CommentRequest commentRequest, // 댓글 글자수 제한 및 빈 값 유효성 검증
            @AuthenticationPrincipal Member member
    ) {
        if (member == null) {
            member = Member.builder()
                    .id(1L)
                    .loginId("test_user")
                    .email("test@example.com")
                    .build();
        }
        return ResponseEntity.ok(commentService.updateComment(commentId,commentRequest,member));
    }
}
