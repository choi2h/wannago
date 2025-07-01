package com.wannago.post.service;

import com.wannago.common.exception.CustomException;
import com.wannago.member.entity.Member;
import com.wannago.member.repository.MemberRepository;
import com.wannago.post.dto.PostLikeCount;
import com.wannago.post.dto.PostsResponse;
import com.wannago.post.entity.Post;
import com.wannago.post.entity.PostLike;
import com.wannago.post.repository.PostLikeRepository;
import com.wannago.post.repository.PostRepository;
import com.wannago.post.service.mapper.PostMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.wannago.common.exception.CustomErrorCode.MEMBER_NOT_EXIST;
import static com.wannago.common.exception.CustomErrorCode.POST_NOT_FOUND;

@RequiredArgsConstructor
@Service
public class PostLikeServiceImpl implements PostLikeService {

    //생성자 주입(@RequiredArgsConstructor)
    private final PostLikeRepository postLikeRepository;
    private final PostRepository postRepository;
    private final MemberRepository memberRepository;
    private final PostMapper postMapper;

    // 좋아요 토글 기능: 게시글에 대한 사용자의 좋아요 상태를 반대로 전환
    @Override
    @Transactional
    public boolean toggleLike(Long postId, Long memberId) {
        // 게시글 유효성 검증
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new CustomException(POST_NOT_FOUND));
        // 사용자 유효성 검증
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new CustomException(MEMBER_NOT_EXIST));
        // Optinal로 좋아요 존재 확인
        Optional<PostLike> like = postLikeRepository.findByPostAndMember(post, member);

        // 좋아요 누른 상태라면 => 좋아요 해제
        if (like.isPresent()) {
            postLikeRepository.delete(like.get());
            return false;
        }
        // 안누른 상태라면 => 좋아요 등록
            postLikeRepository.save(new PostLike(null, post, member));
            return true;

    }

    // 단일 게시글의 총 좋아요 수 조회
    @Override
    public int getLikeCount(Long postId) {
        // 게시글 유효성 검증
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new CustomException(POST_NOT_FOUND));
        return postLikeRepository.countByPost(post);
    }

    // 사용자가 해당 게시글에 좋아요 눌렀는지 빠르게 확인
    @Override
    public boolean hasLiked(Long postId, Long memberId) {
        // 게시글 유효성 검증
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new CustomException(POST_NOT_FOUND));
        // 사용자 유효성 검증
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new CustomException(MEMBER_NOT_EXIST));
        return postLikeRepository.existsByPostAndMember(post, member);
    }
    // 게시글별 좋아요 수 Map 반환
    @Override
    public Map<Long, Integer> getLikeCountMap(List<Long> postIds) {
        List<PostLikeCount> counts = postLikeRepository.countLikesByPostIds(postIds);

        Map<Long, Integer> likeCountMap = new HashMap<>();
        for (PostLikeCount count : counts) {
            likeCountMap.put(count.getPostId(), count.getLikeCount().intValue());
        }

        // postIds에 없는 게시글은 기본값 0으로 넣어줌
        for (Long postId : postIds) {
            likeCountMap.putIfAbsent(postId, 0);
        }

        return likeCountMap;
    }

    // 사용자의 게시글별 좋아요 여부 Map 반환
    @Override
    public Map<Long, Boolean> getLikedMap(List<Long> postIds, Long memberId) {
        Map<Long, Boolean> likedMap = new HashMap<>();

        // 로그인하지 않은 경우 모두 false 처리
        if (memberId == null) {
            for (Long postId : postIds) {
                likedMap.put(postId, false);
            }
            return likedMap;
        }

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new CustomException(MEMBER_NOT_EXIST));

        List<PostLike> likedPosts = postLikeRepository.findByPostIdInAndMember(postIds, member);

        for (PostLike like : likedPosts) {
            likedMap.put(like.getPost().getId(), true);
        }

        // 나머지는 false 처리
        for (Long postId : postIds) {
            likedMap.putIfAbsent(postId, false);
        }

        return likedMap;
    }
}
