package com.wannago.post.service;

import com.wannago.member.entity.Member;
import com.wannago.post.dto.PostsResponse;

import java.util.List;
import java.util.Map;

public interface PostLikeService {
    // 좋아요/취소
    boolean toggleLike(Long postId, Long memberId);
    // 개별 게시글 좋아요 수
    int getLikeCount(Long postId);
    // 개별 게시글 좋아요 여부
    boolean hasLiked(Long postId, Long memberId);
    Map<Long, Integer> getLikeCountMap(List<Long> postIds);
    Map<Long, Boolean> getLikedMap(List<Long> postIds, Long memberId);
}
