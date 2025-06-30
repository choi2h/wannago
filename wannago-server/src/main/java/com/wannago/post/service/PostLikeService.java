package com.wannago.post.service;

import com.wannago.member.entity.Member;
import com.wannago.post.dto.PostsResponse;

public interface PostLikeService {
    // 좋아요/취소
    boolean toggleLike(Long postId, Long memberId);
    // 개별 게시글 좋아요 수
    int getLikeCount(Long postId);
    // 개별 게시글 좋아요 여부
    boolean hasLiked(Long postId, Long memberId);
    // Posts 목록 전체에 대해 likeCount/liked 같이 조회
    PostsResponse getAllPostsWithLikeInfo(Long memberId);
}
