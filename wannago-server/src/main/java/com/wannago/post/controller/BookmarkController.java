package com.wannago.post.controller;

import com.wannago.member.entity.Member;
import com.wannago.post.dto.PostResponse;
import com.wannago.post.dto.PostStatusInfo;
import com.wannago.post.entity.Post;
import com.wannago.post.service.BookmarkService;
import com.wannago.post.service.mapper.PostMapper;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.List;
import java.util.Optional;

import com.wannago.post.repository.PostRepository;

@RestController
@RequiredArgsConstructor
@RequestMapping("/post")
public class BookmarkController {

    private final PostMapper postMapper;
    private final BookmarkService bookmarkService;
    private final PostRepository postRepository;

    // 북마크 토글 요청
    @PostMapping("/{postId}/bookmark")
    public ResponseEntity<Map<String, Boolean>> toggleBookmark(
            @PathVariable Long postId,
            @AuthenticationPrincipal Member member
    ) {
        boolean bookmarked = bookmarkService.toggleBookmark(postId, member);
        return ResponseEntity.ok(Map.of("bookmarked", bookmarked));
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
  //  북마크한 게시글 목록
    @GetMapping("/bookmark")
    public ResponseEntity<List<PostResponse>> getBookmarks(
            @AuthenticationPrincipal Member member
    ) {
        List<Post> posts = bookmarkService.getBookmarks(member);

        // getPostResponse(Post, PostStatusInfo) 사용 (status 기본값 직접 넣음)
        List<PostResponse> response = posts.stream()
                .map(post -> postMapper.getPostResponse(
                        post,
                        new PostStatusInfo(0, false, false)
                ))
                .toList();

        return ResponseEntity.ok(response);
    }

    // 북마크 해제
   @DeleteMapping("/{postId}/bookmark")
    public ResponseEntity<Void> deleteBookmark(
            @PathVariable Long postId,
            @AuthenticationPrincipal Member member
    ) {
        Optional<Post> optionalPost = postRepository.findById(postId);

        if (optionalPost.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Post post = optionalPost.get();
        bookmarkService.deleteBookmark(post, member);
        return ResponseEntity.noContent().build();
    }
}
