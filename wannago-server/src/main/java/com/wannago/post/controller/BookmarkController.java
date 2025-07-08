package com.wannago.post.controller;

import com.wannago.member.entity.Member;
import com.wannago.post.service.BookmarkService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/post")
public class BookmarkController {

    private final BookmarkService bookmarkService;

    // 북마크 등록 요청
    @PostMapping("/{postId}/bookmark")
    public ResponseEntity<Map<String, String>> addBookmark(
          @PathVariable Long postId,
          @AuthenticationPrincipal Member member
    ) {
        bookmarkService.addBookmark(postId, member);
        // 등록 성공 시 201 Created 응답
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("message", "북마크가 성공적으로 등록되었습니다."));
    }

    @DeleteMapping("/{postId}/bookmark")
    public ResponseEntity<Map<String, String>> removeBookmark(
            @PathVariable Long postId,
            @AuthenticationPrincipal Member member
    ) {
        bookmarkService.removeBookmark(postId, member); // BookmarkService에 removeBookmark 메서드 호출
        // 삭제 성공 시 200 OK 응답
        return ResponseEntity.ok(Map.of("message", "북마크가 성공적으로 삭제되었습니다."));
    }

    // 단일 게시글 북마크 여부 조회
    @GetMapping("/{postId}/bookmark")
    public ResponseEntity<Map<String, Boolean>> checkBookmark(
            @PathVariable Long postId,
            @AuthenticationPrincipal Member member
    ) {
        boolean bookmarked = bookmarkService.hasBookmarked(postId, member);
        return ResponseEntity.ok(Map.of("bookmarked", bookmarked));
    }
}
