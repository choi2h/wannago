package com.wannago.post.controller;

import com.wannago.post.service.BookmarkService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/post")
public class BookmarkController {

    private final BookmarkService bookmarkService;

    // 단일 게시글 북마크 여부 조회
    @GetMapping("/{postId}/bookmark")
    public ResponseEntity<Map<String, Boolean>> checkBookmark(
            @PathVariable Long postId,
            @RequestParam Long memberId
    ) {
        boolean bookmarked = bookmarkService.hasBookmarked(postId, memberId);
        return ResponseEntity.ok(Map.of("bookmarked", bookmarked));
    }

    // 북마크 토글 요청
    @PostMapping("/{postId}/bookmark")
    public ResponseEntity<Map<String, Boolean>> toggleBookmark(
            @PathVariable Long postId,
            @RequestParam Long memberId
    ) {
        boolean bookmarked = bookmarkService.toggleBookmark(postId, memberId);
        return ResponseEntity.ok(Map.of("bookmarked", bookmarked));
    }

}
