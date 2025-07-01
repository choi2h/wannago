package com.wannago.post.controller;

// import com.wannago.auth.LoginUser; // ← auth? 구현되면
import com.wannago.post.service.PostLikeService;
import com.wannago.post.dto.PostsResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/post")
public class PostLikeController {

    private final PostLikeService postLikeService;

    // 단일 게시글에 대한 좋아요 수 & 좋아요 상태 조회 (로그인 없이 테스트 용도)
    @GetMapping("/{postId}/like")
    public ResponseEntity<Map<String, Object>> getPostLikeInfoWithoutLogin(
            @PathVariable Long postId,
            @RequestParam(required = false) Long memberId
    ) {
        int likeCount = postLikeService.getLikeCount(postId);
        boolean liked = memberId != null && postLikeService.hasLiked(memberId, postId);

        return ResponseEntity.ok(Map.of(
                "postId", postId,
                "likeCount", likeCount,
                "liked", liked
        ));
    }

    // 좋아요 토글 요청 (좋아요 등록/취소)
    @PostMapping("/{postId}/like")
    // 로그인 기능 구현 없는 상태 => 좋아요 API 테스트 용 임시 메서드
    public ResponseEntity<Map<String, Boolean>> toggleLikeWithoutLogin(
            @PathVariable Long postId,
            @RequestParam(required = false) Long memberId // 쿼리 파라미터로 강제 전달 /like?memberId=5
    ) {
        boolean liked = postLikeService.toggleLike(postId, memberId);
        return ResponseEntity.ok(Map.of("liked", liked));
    }
    // @PostMapping("/{postId}/like")
//    public ResponseEntity<Map<String, Boolean>> toggleLike(
//            @PathVariable Long postId,
//            @AuthenticationPrincipal LoginUser loginUser
//    ) {
//        if (loginUser == null) { // 로그인
//            return ResponseEntity.status(401).body(Map.of("liked", false));
//        }
//
//        boolean liked = postLikeService.toggleLike(postId, loginUser.getId());
//        return ResponseEntity.ok(Map.of("liked", liked));
//    }

    // 전체 게시글 정보 + 좋아요 수 & 좋아요 상태 조회
    // 로그인 기능 구현 없는 상태 => 좋아요 API 테스트 용 임시 메서드
    @GetMapping("/like")
    public ResponseEntity<PostsResponse> getAllPostsWithLikeInfoWithoutLogin(
            @RequestParam(required = false) Long memberId // /like?memberId=5
    ) {
        PostsResponse response = postLikeService.getAllPostsWithLikeInfo(memberId);
        return ResponseEntity.ok(response);
    }
//    @GetMapping("/with-likes")
//    public ResponseEntity<PostsResponse> getAllPostsWithLikeInfo(
//            @AuthenticationPrincipal LoginUser loginUser
//    ) {
//        Long memberId = loginUser != null ? loginUser.getId() : null;
//        PostsResponse response = postLikeService.getAllPostsWithLikeInfo(memberId);
//        return ResponseEntity.ok(response);
//    }
}

